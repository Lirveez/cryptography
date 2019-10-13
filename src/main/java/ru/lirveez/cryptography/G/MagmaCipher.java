package ru.lirveez.cryptography.G;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class MagmaCipher {

    private static final int[][] S_BLOCK = {
            {12, 4, 6, 2, 10, 5, 11, 9, 14, 8, 13, 7, 0, 3, 15, 1},
            {6, 8, 2, 3, 9, 10, 5, 12, 1, 14, 4, 7, 11, 13, 0, 15},
            {11, 3, 5, 8, 2, 15, 10, 13, 14, 1, 7, 4, 12, 9, 6, 0},
            {12, 8, 2, 1, 13, 4, 15, 6, 7, 0, 10, 5, 3, 14, 9, 11},
            {7, 15, 5, 10, 8, 1, 6, 13, 0, 9, 3, 14, 11, 4, 2, 12},
            {5, 13, 15, 6, 9, 2, 12, 10, 11, 7, 8, 1, 4, 3, 14, 0},
            {8, 14, 2, 5, 6, 9, 1, 12, 15, 4, 11, 0, 13, 10, 3, 7},
            {1, 7, 14, 13, 0, 5, 8, 3, 4, 15, 10, 6, 9, 12, 11, 2}
    };

    private final BigInteger[] splitKeys;

    public MagmaCipher(BigInteger key) {
        val mask = new BigInteger("11111111111111111111111111111111", 2);
        splitKeys = new BigInteger[8];
        Arrays.setAll(splitKeys, i -> {
            val shift = (8 - i - 1) * 32;
            log.info("Shift: {}", shift);
            val keyBlock = key.and(mask.shiftLeft(shift)).shiftRight(shift);
            log.info("Key block {}", keyBlock.toString(2));
            return keyBlock;
        });
    }

    public BigInteger encode(BigInteger number) {
        return splitIntoBlocks(number)
                .stream()
                .sequential()
                .map(this::encodeBlock)
                .reduce(BigInteger.ZERO, (bigInteger, bigInteger2) -> {
                    log.info("Got number 1 '{}' and number 2 '{}'", bigInteger.toString(2), bigInteger2.toString(2));
                    return bigInteger.shiftLeft(64).or(bigInteger2);
                });
    }

    public BigInteger decode(BigInteger number) {
        log.info("Decoding...");
        return splitIntoBlocks(number)
                .stream()
                .sequential()
                .map(this::decodeBlock)
                .reduce(BigInteger.ZERO, (bigInteger, bigInteger2) -> {
                    log.info("Got number 1 '{}' and number 2 '{}'", bigInteger.toString(2), bigInteger2.toString(2));
                    return bigInteger.shiftLeft(64).or(bigInteger2);
                });

    }

    private BigInteger decodeBlock(BigInteger number) {
        val mask = new BigInteger("00000000000000000000000000000000" +
                "11111111111111111111111111111111", 2);

        var left = number.shiftRight(32);
        var right = number.and(mask);

        log.info("Left and right: {}, {}", left.toString(2), right.toString(2));

        for (int i = 0; i < 8; i++) {
            log.info("Using key {}", i % 8);
            val newRight = doIteration(left, right, splitKeys[i % 8]);
            left = right;
            right = newRight;
        }
        for (int i = 8; i < 32; i++) {
            log.info("Using key {}", 7 - i % 8);
            val newRight = doIteration(left, right, splitKeys[7 - i % 8]);
            left = right;
            right = newRight;
        }
        left = doIteration(left, right, splitKeys[0]);

        return left.shiftLeft(32).or(right);
    }

    //64 bit
    private BigInteger encodeBlock(BigInteger number) {
        val mask = new BigInteger("00000000000000000000000000000000" +
                "11111111111111111111111111111111", 2);

        var left = number.shiftRight(32);
        var right = number.and(mask);

        log.info("Left and right: {}, {}", left.toString(2), right.toString(2));

        for (int i = 0; i < 24; i++) {
            log.info("Using key {}", i % 8);
            val newRight = doIteration(left, right, splitKeys[i % 8]);
            left = right;
            right = newRight;
        }
        for (int i = 24; i < 32; i++) {
            log.info("Using key {}", 7 - i % 8);
            val newRight = doIteration(left, right, splitKeys[7 - i % 8]);
            left = right;
            right = newRight;
        }

        left = doIteration(left, right, splitKeys[0]);

        return left.shiftLeft(32).or(right);
    }

    //Returns new value for right
    private BigInteger doIteration(BigInteger left, BigInteger right, BigInteger key) {
        var newRight = right.add(key).and(new BigInteger("11111111111111111111111111111111", 2));
        newRight = splitReplaceAndConcat(newRight);
        newRight = cyclicLeftShift(newRight);
        newRight = left.xor(newRight);
        return newRight;
    }

    protected BigInteger cyclicLeftShift(BigInteger number) {
        val mask = new BigInteger("11111111111000000000000000000000", 2);
        val cutMask = new BigInteger("11111111111111111111111111111111", 2);

        return number
                .shiftLeft(11)
                .or(
                        number
                                .and(mask)
                                .shiftRight(21)
                )
                .and(cutMask);
    }

    protected BigInteger splitReplaceAndConcat(BigInteger number) {
        val mask = new BigInteger("1111", 2);
        var result = BigInteger.ZERO;

        for (int i = 0; i < 8; i++) {
            val shift = (i - 1) * 4;
            val currentNumber = number.and(mask.shiftLeft(shift)).shiftRight(shift);
            val replaced = S_BLOCK[i][currentNumber.intValue()];
            result = result.shiftLeft(4).or(BigInteger.valueOf((long) replaced));
        }
        return result;
    }

    private Collection<BigInteger> splitIntoBlocks(BigInteger number) {
        val longMask = new BigInteger("1111111111111111111111111111111111111111111111111111111111111111", 2);
        var temp = number;
        val blocks = new ArrayList<BigInteger>(number.bitLength() / 64 + 1);
        while (!temp.equals(BigInteger.ZERO)) {
            blocks.add(temp.and(longMask));
            temp = temp.shiftRight(64);
        }
        log.info("Split is: {}", blocks.stream().map(bi -> bi.toString(2)).collect(Collectors.toList()));

        return blocks;
    }

}

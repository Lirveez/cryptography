package ru.lirveez.cryptography.G;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.lirveez.cryptography.CypherInterface;

import java.util.Random;

import static org.apache.commons.lang.ArrayUtils.addAll;

@Data
@Slf4j
public class MagmaCypher implements CypherInterface {

    private final Random random = new Random();

    private final long C1 = 0b11100100011000011111111;
    private final long C2 = 0b1111111010101100010011010100000;
    private final long[][] sBlocks = {
            {12, 4, 6, 2, 10, 5, 11, 9, 14, 8, 13, 7, 0, 3, 15, 1},
            {6, 8, 2, 3, 9, 10, 5, 12, 1, 14, 4, 7, 11, 13, 0, 15},
            {11, 3, 5, 8, 2, 15, 10, 13, 14, 1, 7, 4, 12, 9, 6, 0},
            {12, 8, 2, 1, 13, 4, 15, 6, 7, 0, 10, 5, 3, 14, 9, 11},
            {7, 15, 5, 10, 8, 1, 6, 13, 0, 9, 3, 14, 11, 4, 2, 12},
            {5, 13, 15, 6, 9, 2, 12, 10, 11, 7, 8, 1, 4, 3, 14, 0},
            {8, 14, 2, 5, 6, 9, 1, 12, 15, 4, 11, 0, 13, 10, 3, 7},
            {1, 7, 14, 13, 0, 5, 8, 3, 4, 15, 10, 6, 9, 12, 11, 2}};
    private final int[] key = {
            0b1110011101101110011111011101101,
            0b1110110111110111001111011101,
            0b11011101010111100111100111011,
            0b1100011011100110110101101101111,
            0b110001111100111010111010111111,
            0b110011011100111011011101011111,
            0b1111111111111111111111111,
            0b1101010110010010011111
    };

    private long synch;

    @Override
    public String encode(String text) {
        log.info("Encoding: {}", text);
        char[] sourceChars = text.toCharArray();
        int missingBlockBits = 64 - sourceChars.length % 64;
        char[] charsToEncode = addAll(sourceChars, new char[missingBlockBits]);
        String result = magmaCycle(charsToEncode);
        log.info("Result: {}", result);
        return result;
    }

    @Override
    public String decode(String text) {
        log.info("Decoding: {}", text);
        char[] charsText = text.toCharArray();
        String result = magmaCycle(charsText);
        log.info("Result: {}", result);
        return result;
    }

    private String magmaCycle(char[] text) {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < text.length; j += 4) {
            long blockToEncode = getLongFromChars(text[j],
                    text[j + 1],
                    text[j + 2],
                    text[j + 3]);
            long iterationResult;
            int keyIndex = (j / 4) % sBlocks.length;
            int magmaIteration = (j / 4) % 64;
            log.info("Iteration: {}, synch: {}", magmaIteration, Long.toBinaryString(synch));
            if (magmaIteration <= 23) {
                iterationResult = iterate(blockToEncode, key[keyIndex]);
            } else {
                iterationResult = iterate(blockToEncode, key[(sBlocks.length - 1) - keyIndex]);
            }
            char[] iterationChars = getCharsFromLong(iterationResult);
            result.append(new String(iterationChars));
        }
        return result.toString();
    }

    private long getLongFromChars(char c1, char c2, char c3, char c4) {
        return (long) c1 << 48 | (long) c2 << 32 | (long) c3 << 16 | (long) c4;
    }

    private char[] getCharsFromLong(long itterationResult) {
        long[] thirtyTwoBits = splitSixtyFourBit(itterationResult);
        long[] leftPart = splitThirtyTwoBit(thirtyTwoBits[0]);
        long[] rightPart = splitThirtyTwoBit(thirtyTwoBits[1]);
        char[] result = new char[4];
        result[0] = (char) leftPart[0];
        result[1] = (char) leftPart[1];
        result[2] = (char) rightPart[0];
        result[3] = (char) rightPart[1];
        return result;
    }

    private long iterate(long text, long key) {
        long[] blocks = splitSixtyFourBit(synch);
        long leftBlock = blocks[0];
        long rightBlock = blocks[1];
        long leftConverted = (leftBlock + C1) % 0b11111111111111111111111111111111L;
        long rightConverted = (rightBlock + C2) % 0b100000000000000000000000000000000L;
        rightBlock = (rightConverted + key) % 0b100000000000000000000000000000000L;
        long[] twoBlocks = splitThirtyTwoBit(rightBlock);
        long[] fourBlocks = addAll(splitSixteenBit(twoBlocks[0]), splitSixteenBit(twoBlocks[1]));
        long[] eightBlocks = addAll(addAll(splitEightBit(fourBlocks[0]), splitEightBit(fourBlocks[1])),
                addAll(splitEightBit(fourBlocks[2]), splitEightBit(fourBlocks[3])));
        long[] transformation = transformBlocks(eightBlocks);
        long combineResult = combineEightBlocks(transformation);
        long iterationResult = cycleLeft(combineResult);
        rightBlock = leftConverted ^ iterationResult;
        leftBlock = iterationResult;
        synch = (leftBlock << 32) | rightBlock;
        return text ^ synch;
    }

    private long combineEightBlocks(long[] blocks) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result |= blocks[i] << ((7 - i) * 4);
        }
        return result;
    }

    private long cycleLeft(long block) {
        long rightPart = block >> 21;
        long leftPart = (block & 0b111111111111111111111) << 11;
        return leftPart | rightPart;
    }

    private long[] transformBlocks(long[] blocks) {
        long[] result = new long[8];
        for (int i = 0; i < 8; i++) {
            int index = (int) blocks[i];
            result[i] = sBlocks[i][index];
        }
        return result;
    }

    private long[] splitEightBit(long block) {
        long[] splitted = new long[2];
        splitted[0] = block >> 4;
        splitted[1] = block & 0b1111L;
        return splitted;
    }

    private long[] splitSixteenBit(long block) {
        long[] splitted = new long[2];
        splitted[0] = block >> 8;
        splitted[1] = block & 0b11111111L;
        return splitted;
    }

    private long[] splitThirtyTwoBit(long block) {
        long[] splitted = new long[2];
        splitted[0] = block >> 16;
        splitted[1] = block & 0b1111111111111111L;
        return splitted;
    }

    private long[] splitSixtyFourBit(long block) {
        long[] splitted = new long[2];
        splitted[0] = block >> 32;
        splitted[1] = block & 0b11111111111111111111111111111111L;
        return splitted;
    }
}

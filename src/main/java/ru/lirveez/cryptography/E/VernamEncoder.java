package ru.lirveez.cryptography.E;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.lirveez.cryptography.Encoder;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
public class VernamEncoder implements Encoder {

    private final List<Integer> alphabet;
    private final BigInteger key;
    private final int alphabetBase;
    private final int mask;
    private BigInteger randomKey;

    public VernamEncoder(String alphabet, BigInteger key) {
        this.alphabet = alphabet.chars().sequential().boxed().collect(Collectors.toList());
        this.key = key;
        alphabetBase = log2(alphabet.length());
        if (alphabetBase == -1)
            throw new IllegalStateException("Alphabet size should be base of 2");
        var currMask = 0;
        for (int i = 0; i < alphabetBase; i++) {
            currMask = (currMask << 1) | 1;
        }
        this.mask = currMask;
    }

    public VernamEncoder(String alphabet) {
        this.alphabet = alphabet.chars().sequential().boxed().collect(Collectors.toList());
        this.key = BigInteger.ONE;
        alphabetBase = log2(alphabet.length());
        if (alphabetBase == -1)
            throw new IllegalStateException("Alphabet size should be base of 2");
        var currMask = 0;
        for (int i = 0; i < alphabetBase; i++) {
            currMask = (currMask << 1) | 1;
        }
        this.mask = currMask;
    }


    protected int log2(int number) {
        var current = number;
        int base = 0;
        while (current > 1 && (current & 1) == 0) {
            current = current >> 1;
            base++;
        }
        return current == 1 ? base : -1;
    }

    public String encodeWithRandomKey(String input) {
        randomKey = new BigInteger(input.length() * alphabetBase, new Random());

        return encodeWithKey(input, randomKey);
    }

    private String encodeWithKey(String input, BigInteger key) {
        val binaryInput = input.chars()
                .sequential()
                .map(alphabet::indexOf)
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger.ZERO, (bigInteger, bigInteger2) -> bigInteger.shiftLeft(alphabetBase).or(bigInteger2));

        val binaryEncoded = binaryInput.xor(key);
        log.info("Binary input is: {}", binaryInput.toString(2));
        log.info("Binary key is:   {}", key.toString(2));
        log.info("Binary encoded:  {}", binaryEncoded.toString(2));

        val builder = new StringBuilder();
        var temp = binaryEncoded;
        for (var i = 0; i < input.length(); i++) {
            val code = temp.and(BigInteger.valueOf(mask)).intValue();
            builder.append((char) alphabet.get(code).intValue());
            temp = temp.shiftRight(alphabetBase);
        }

        return builder.reverse().toString();
    }

    @Override
    public String encode(String input) {
        if (input.length() * alphabetBase < key.bitCount())
            throw new IllegalArgumentException("Key length is larger that input text");
        return encodeWithKey(input, key);
    }

    @Override
    public String decode(String input) {
        return encode(input);
    }

    public String decodeWithKey(String input) {
        return encodeWithKey(input, randomKey);
    }
}

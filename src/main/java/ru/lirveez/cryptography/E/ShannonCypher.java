package ru.lirveez.cryptography.E;

import lombok.extern.slf4j.Slf4j;
import ru.lirveez.cryptography.Alphabet;
import ru.lirveez.cryptography.CypherInterface;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
public class ShannonCypher implements CypherInterface {

    private Random random = new Random();
    private int[] key;

    @Override
    public String encode(String text) {
        log.info("Encoding: {}", text);
        int[] textArray = text.codePoints()
                .map(Alphabet::getLetterPosition)
                .toArray();
        int[] arrayResult = new int[textArray.length];
        int[] key = getKey(textArray.length);
        for (int i = 0; i < textArray.length; i++) {
            arrayResult[i] = textArray[i] ^ key[i];
        }
        String result = Arrays.stream(arrayResult)
                .mapToObj(letter -> (char) letter)
                .map(String::valueOf)
                .collect(Collectors.joining());
        log.info("Result: {}", result);
        return result;
    }

    @Override
    public String decode(String text) {
        log.info("Decoding: {}", text);
        int[] textArray = text.codePoints()
                .map(Alphabet::getLetterPosition)
                .toArray();
        int[] arrayResult = new int[textArray.length];

        int[] key = getKey(textArray.length);
        for (int i = 0; i < textArray.length; i++) {

            arrayResult[i] = (byte) (textArray[i] ^ key[i]);
        }
        String result = Arrays.stream(arrayResult)
                .mapToObj(Alphabet::getLetterByPosition)
                .map(String::valueOf)
                .collect(Collectors.joining());
        log.info("Result: {}", result);
        return result;
    }

    private int[] getKey(int length) {
        if (key == null) {
            key = new int[length];
            for (int i = 0; i < length; i++) {
                key[i] = random.nextInt(32);
            }
        }
        log.info("Key: {}",key);
        return key;
    }
}

package ru.lirveez.cryptography.B;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.lirveez.cryptography.Encoder;

import java.util.List;

@Slf4j
public class VigenereEncoder implements Encoder {

    private final List<Integer> alphabet;
    private final int[] key;

    public VigenereEncoder(List<Integer> alphabet, String key) {
        this.alphabet = alphabet;
        this.key = key.chars().toArray();
    }

    @Override
    public String encode(String input) {
        val stringBuilder = new StringBuilder(input.length());
        val ints = input.chars().toArray();
        for (var i = 0; i < ints.length; i++) {
            val inputSymbol = ints[i];
            val inputTextAlphabetIndex = alphabet.indexOf(inputSymbol);
            if (inputTextAlphabetIndex == -1)
                throw new IllegalStateException(String.format("Unexpected symbol in given text: '%s'", (char) inputSymbol));
            val keyAlphabetIndex = alphabet.indexOf(key[i % key.length]);
            stringBuilder.append((char) alphabet.get((inputTextAlphabetIndex + keyAlphabetIndex) % alphabet.size()).intValue());

        }
        return stringBuilder.toString();
    }

    @Override
    public String decode(String input) {
        return null;
    }
}

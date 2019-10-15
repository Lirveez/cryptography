package ru.lirveez.cryptography.A;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.lirveez.cryptography.Encoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public class ReplacementEncoder implements Encoder {

    private final List<Integer> alphabet;

    @Override
    public String encode(String input) {
        return Stream.of(input.split(" "))
                .map(this::encodeWord)
                .collect(Collectors.joining(" "));
    }

    private String encodeWord(String word) {
        return word
                .chars()
                .map(this::replaceSymbol)
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private int replaceSymbol(int symbol) {
        val index = alphabet.indexOf(symbol);
        if (index < 0)
            throw new IllegalStateException(String.format("Unexpected symbol: '%s'", (char) symbol));
        return alphabet.get(alphabet.size() - 1 - index);
    }

    @Override
    public String decode(String input) {
        return encode(input);
    }
}

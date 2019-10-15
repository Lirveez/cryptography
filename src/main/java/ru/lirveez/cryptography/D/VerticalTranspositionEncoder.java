package ru.lirveez.cryptography.D;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.lirveez.cryptography.Encoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class VerticalTranspositionEncoder implements Encoder {

    private final List<Integer> key;

    public VerticalTranspositionEncoder(Integer... key) {
        val keyList = List.of(key);
        for (int i = 0; i < key.length; i++) {
            if (!keyList.contains(i + 1))
                throw new IllegalStateException("Does not contain: " + i);
        }

        this.key = Stream.of(key)
                .map(i -> i - 1)
                .collect(Collectors.toList());
    }

    @Override

    public String encode(String input) {
        val table = new ArrayList<ArrayList<Character>>();
        val keyLength = key.size();
        for (int i = 0; i < keyLength; i++) {
            table.add(i, new ArrayList<>());
        }

        val noSpaces = input.replace(" ", "");

        for (int i = 0; i < noSpaces.length(); i++) {
            table.get(i % keyLength).add(noSpaces.charAt(i));
        }
        log.debug("{}", table);

        return key.stream()
                .map(table::get)
                .flatMap(Collection::stream)
                .map(String::valueOf)
                .collect(Collectors.joining());

    }

    @Override
    public String decode(String input) {
        val keyLength = key.size();
        val columnLength = input.length() / keyLength;
        val overFilledColumns = input.length() % keyLength;
        val table = new ArrayList<ArrayList<Character>>(keyLength);
        for (int i = 0; i < keyLength; i++) {
            table.add(i, new ArrayList<>());
        }

        int inputIndex = 0;
        for (int i = 0; i < keyLength; i++) {
            val columnToWrite = key.get(i);
            for (int j = 0; j < columnLength + (columnToWrite < overFilledColumns ? 1 : 0); j++) {
                table.get(columnToWrite).add(input.charAt(inputIndex++));
            }
        }

        log.debug("{}", table);
        val result = new StringBuilder();
        var currentIndex = 0;
        var hasSymbols = true;
        while (hasSymbols) {
            for (val characters : table) {
                if (characters.size() == currentIndex) {
                    hasSymbols = false;
                    break;
                }
                result.append(characters.get(currentIndex));

            }
            currentIndex++;
        }
        return result.toString();
    }

}



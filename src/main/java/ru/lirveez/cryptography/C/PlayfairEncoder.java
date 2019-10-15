package ru.lirveez.cryptography.C;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.lirveez.cryptography.Encoder;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Slf4j
public class PlayfairEncoder implements Encoder {

    private final int[][] matrix;
    private final int rows = 5, cols = 6;


    public PlayfairEncoder(String key) {
        matrix = new int[rows][cols];
        val symbolSequence = key.toUpperCase() + "АБВГДЕЖЗИКЛМНОПРСТУФХЦЧШЩЫЬЭЮЯ";
        val orderedSymbols = new LinkedList<Integer>();
        symbolSequence.chars()
                .distinct()
                .forEach(orderedSymbols::addLast);
        log.debug("Stack[{}] = {}", orderedSymbols.size(), orderedSymbols.stream().map(i -> (char) i.intValue()).map(String::valueOf).collect(joining()));
        for (int i = 0; i < orderedSymbols.size(); i++) {
            matrix[i / cols][i % cols] = orderedSymbols.get(i);
        }
        for (int i = 0; i < rows; i++) {
            var row = Arrays.stream(matrix[i])
                    .mapToObj(integer -> (char) integer)
                    .map(String::valueOf)
                    .collect(joining(" ", "[", "]"));
            log.info(row);
        }
    }

    @Override
    public String encode(String input) {
        log.info("Got: {}", input);
        val strip = input.replace(" ", "");

        var paired = getPaired(strip);
        log.info("Initial paired: {}", paired);
        while (paired.matches(".+?(.)\\1.+")) {
            paired = getPaired(
                    paired
                            .replaceFirst("(.)\\1", "$1Х$1")
                            .replace(" ", "")
            );

            log.info("Got paired after iteration: {}", paired);
        }

        if (paired.matches(".+? (.)"))
            paired = paired.replaceAll(" (.)$", " $1Х");
        log.info("Final: {}", paired);

        return Stream.of(paired.split(" "))
                .map(pair -> replacePair(pair, 1))
                .collect(joining());
    }

    protected String replacePair(String pair, int directionMove) {
        int firstCharRow = -1, firstCharColumn = -1, secondCharRow = -1, secondCharColumn = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (pair.charAt(0) == (char) matrix[i][j]) {
                    firstCharColumn = j;
                    firstCharRow = i;
                }

                if (pair.charAt(1) == (char) matrix[i][j]) {
                    secondCharColumn = j;
                    secondCharRow = i;
                }
            }
        }

        if (firstCharColumn == -1)
            throw new IllegalStateException("Unexpected symbol: " + pair.charAt(0));
        if (secondCharColumn == -1)
            throw new IllegalStateException("Unexpected symbol: " + pair.charAt(1));

        String replaced;
        if (firstCharRow == secondCharRow) {
            replaced = "" + (char) matrix[firstCharRow][(firstCharColumn + directionMove) % matrix[firstCharRow].length] + (char) matrix[secondCharRow][(secondCharColumn + directionMove) % matrix[secondCharRow].length];
        } else if (firstCharColumn == secondCharColumn) {
            replaced = "" + (char) matrix[(firstCharRow + directionMove) % matrix.length][firstCharColumn] + (char) matrix[(secondCharRow + directionMove) % matrix.length][secondCharColumn];
        } else {
            replaced = "" + (char) matrix[firstCharRow][secondCharColumn] + (char) matrix[secondCharRow][firstCharColumn];
        }
        return replaced;
    }

    protected String getPaired(String string) {
        val stringBuilder = new StringBuilder();
        var previousLetters = 0;
        for (int i = 0; i < string.length(); i++) {
            if (previousLetters == 2) {
                stringBuilder.append(' ');
                previousLetters = 0;
            }
            stringBuilder.append(string.charAt(i));
            previousLetters++;
        }
        return stringBuilder.toString();
    }

    @Override
    public String decode(String input) {
        return Stream.of(getPaired(input).split(" "))
                .map(pair -> replacePair(pair, 4))
                .collect(joining());
    }
}

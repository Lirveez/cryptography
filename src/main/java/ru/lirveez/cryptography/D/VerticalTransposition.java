package ru.lirveez.cryptography.D;

import lombok.extern.slf4j.Slf4j;
import ru.lirveez.cryptography.CypherInterface;

@Slf4j
public class VerticalTransposition implements CypherInterface {

    private int columns = 7;
    private int rows = 4;
    private int[] key = {5, 4, 1, 7, 2, 6, 3};
    private char[][] table = new char[rows][columns];

    @Override
    public String encode(String text) {
        log.info("Encoding: {}", text);
        String textWithoutSpaces = text.replace(" ", "");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int index = i * columns + j;
                if (index < textWithoutSpaces.length()) {
                    char letter = textWithoutSpaces.charAt(index);
                    if (i % 2 == 0)
                        table[i][j] = letter;
                    else
                        table[i][columns - j - 1] = letter;
                }
            }
            log.info("Table[{}]: {}", i, table[i]);
        }
        StringBuilder result = new StringBuilder();
        for (int column : key)
            for (int i = 0; i < rows; i++)
                result.append(table[i][column - 1]);
        String encoded = result.toString();
        log.info("Result: {}", encoded);
        return encoded;
    }

    @Override
    public String decode(String text) {
        log.info("Decoding: {}", text);
        for (int i = 0; i < rows * columns; i++) {
            int column = key[i / rows] - 1;
            table[i % rows][column] = text.charAt(i);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                if (i % 2 == 0)
                    result.append(table[i][j]);
                else
                    result.append(table[i][columns - j - 1]);
            log.info("Table[{}]: {}", i, table[i]);
        }
        String decoded = result.toString();
        log.info("Result: {}", decoded);
        return decoded;
    }
}

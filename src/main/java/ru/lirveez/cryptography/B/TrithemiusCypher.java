package ru.lirveez.cryptography.B;

import ru.lirveez.cryptography.CypherInterface;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.lirveez.cryptography.Alphabet.*;

public class TrithemiusCypher implements CypherInterface {

    private final int[][] cypherUpperTable = initCypherTable(firstUpperLetterCode);
    private final int[][] cypherTable = initCypherTable(firstLetterCode);

    @Override
    public String encode(String text) {
        AtomicInteger position = new AtomicInteger(0);
        return text.chars()
                .boxed()
                .map(letterCode -> getAliasLetterCode(letterCode, position.getAndIncrement()))
                .map(Character::toString)
                .collect(Collectors.joining());
    }

    @Override
    public String decode(String text) {
        return encode(text);
    }

    private Integer getAliasLetterCode(Integer code, Integer position) {
        if (code >= firstLetterCode
                && code <= lastLetterCode)
            return cypherTable[firstLetterCode - code][position % size];
        else if (code >= firstUpperLetterCode
                && code <= lastUpperLetterCode)
            return cypherUpperTable[firstLetterCode - code][position % size];
        return code;
    }

    private int[][] initCypherTable(int start) {
        int[][] table = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = i; j < i + size; j++)
                if (j > size - 1)
                    table[i][j - i] = start - size - 2 + j;
                else
                    table[i][j - i] = start + j;
        return table;
    }
}

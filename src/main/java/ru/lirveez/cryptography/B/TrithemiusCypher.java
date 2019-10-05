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
            return cypherTable[firstLetterCode - code][position % (size + 1)];
        else if (code >= firstUpperLetterCode
                && code <= lastUpperLetterCode)
            return cypherUpperTable[firstLetterCode - code][position % (size + 1)];
        return code;
    }

    private int[][] initCypherTable(int start) {
        int[][] table = new int[size + 1][size + 1];
        for (int i = 0; i < size + 1; i++)
            for (int j = i; j < i + size + 1; j++)
                if (j > size)
                    table[i][j - i] = start - size - 1 + j;
                else
                    table[i][j - i] = start + j;
        return table;
    }
}

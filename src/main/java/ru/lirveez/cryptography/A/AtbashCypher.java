package ru.lirveez.cryptography.A;

import ru.lirveez.cryptography.CypherInterface;

import java.util.stream.Collectors;

import static ru.lirveez.cryptography.Alphabet.*;

public class AtbashCypher implements CypherInterface {

    public String encode(String text) {
        return text.chars()
                .boxed()
                .map(this::getAliasLetterCode)
                .map(Character::toString)
                .collect(Collectors.joining());
    }

    public String decode(String text) {
        return encode(text);
    }

    private Integer getAliasLetterCode(Integer code) {
        if (code >= firstLetterCode
                && code <= lastLetterCode)
            return firstLetterCode + lastLetterCode - code;
        else if (code >= firstUpperLetterCode
                && code <= lastUpperLetterCode)
            return firstUpperLetterCode + lastUpperLetterCode - code;
        return code;
    }

}

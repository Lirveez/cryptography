package ru.lirveez.cryptography.A;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.lirveez.cryptography.CypherInterface;

import java.util.stream.Collectors;

import static ru.lirveez.cryptography.Alphabet.*;

@AllArgsConstructor
@Slf4j
public class CaesarCypher implements CypherInterface {

    private int offset;

    @Override
    public String encode(String text) {
        return text.chars()
                .boxed()
                .map(letterCode -> getOffsetLetter(letterCode, offset))
                .map(Character::toString)
                .collect(Collectors.joining());
    }

    @Override
    public String decode(String text) {
        return text.chars()
                .boxed()
                .map(letterCode -> getOffsetLetter(letterCode, offset * -1))
                .map(Character::toString)
                .collect(Collectors.joining());
    }

    private Integer getOffsetLetter(int letterCode, int offset) {
        var oldPosition = getLetterPosition(letterCode) % (size + 1);
        var newPosition = oldPosition + offset;
        if (newPosition < 0) {
            newPosition = newPosition + size;
        } else if (newPosition >= size) {
            newPosition = newPosition - size;
        }
        if (isUpper(letterCode)) {
            return firstUpperLetterCode + newPosition;
        } else {
            return firstLetterCode + newPosition;
        }
    }
}

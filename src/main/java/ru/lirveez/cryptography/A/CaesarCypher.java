package ru.lirveez.cryptography.A;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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

    class B {

    }

//    static {
//        class C{
//
//        }
//        log.info("Class C {}", C.class);
//    }

    public static void main(String[] args) {
//        class A {
//
//        }
//
//        log.info("Class A {}", A.class);
//        log.info("Class B {}", B.class);


        val offset = 10;
        val caesarCypher = new CaesarCypher(offset);
        var text = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
        log.info("Encoding: {}", text);
        log.info("Offset: {}", offset);

        var encoded = caesarCypher.encode(text);

        log.info("Зашифровано: {}", encoded);
        log.info("Расшифровано: {}", caesarCypher.decode(encoded));
    }
}

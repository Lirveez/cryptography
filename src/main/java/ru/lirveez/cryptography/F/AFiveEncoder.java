package ru.lirveez.cryptography.F;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.lirveez.cryptography.Encoder;

import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AFiveEncoder implements Encoder {

    private final String alphabet;
    private final long key;

    private static final long X_MASK = ((long) 0b1111111111111111111) << 45;
    private static final long Y_MASK = ((long) 0b1111111111111111111111) << 23;
    private static final long Z_MASK = 0b11111111111111111111111;


    private int registerX, registerY, registerZ;

    @Override
    public String encode(String input) {
        val symbolToCode = new HashMap<Integer, Integer>(alphabet.length() * 2);
        for (int i = 0; i < alphabet.length(); i++) {
            symbolToCode.put(alphabet.codePointAt(i), i);
        }

        registerX = (int) ((key & X_MASK) >>> 45);
        registerY = (int) ((key & Y_MASK) >>> 23);
        registerZ = (int) (key & Z_MASK);

        return input.chars()
                .map(symbolToCode::get)
                .sequential()
                .map(this::encodeSymbol)
                .map(alphabet::codePointAt)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
    }

    private int encodeSymbol(int currentSymbolCode) {
        int base = 4;
        int key = 0;
        for (int i = 0; i < base; i++) {
            int maj = (getBitAt(registerX, 8) & getBitAt(registerY, 10)) |
                    (getBitAt(registerX, 8) & getBitAt(registerZ, 10)) |
                    (getBitAt(registerY, 10) & getBitAt(registerZ, 10));

            int currentBit = 0;

            if (getBitAt(registerX, 8) == maj) {
                currentBit = currentBit ^ getBitAt(registerX, 18);
                int firstBit = getBitAt(registerX, 13) ^ getBitAt(registerX, 16) ^ getBitAt(registerX, 17) ^ getBitAt(registerX, 18);
                registerX = (registerX << 1) | firstBit;
            }
            if (getBitAt(registerY, 10) == maj) {
                currentBit = currentBit ^ getBitAt(registerY, 21);
                int firstBit = getBitAt(registerY, 21) ^ getBitAt(registerZ, 20);
                registerY = (registerY << 1) | firstBit;
            }
            if (getBitAt(registerZ, 10) == maj) {
                currentBit = currentBit ^ getBitAt(registerZ, 22);
                int firstBit = getBitAt(registerZ, 22) ^ getBitAt(registerZ, 21) ^ getBitAt(registerZ, 20) ^ getBitAt(registerZ, 7);
                registerZ = (registerZ << 1) | firstBit;
            }
            key = (key << 1) | currentBit;
        }
        return (key ^ currentSymbolCode);
    }

    public static int getBitAt(int value, int position) {
        return (value & (1 << position)) >>> position;
    }

    @Override
    public String decode(String input) {
        return encode(input);
    }
}

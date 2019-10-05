package ru.lirveez.cryptography.F;

import lombok.extern.slf4j.Slf4j;
import ru.lirveez.cryptography.Alphabet;
import ru.lirveez.cryptography.CypherInterface;

import java.util.stream.Collectors;

@Slf4j
public class A5FirstCypher implements CypherInterface {

    private int xRegister =     0b1100000100010011101;
    private int yRegister =  0b0011001110010111001110;
    private int zRegister = 0b10000100110111000101110;

    @Override
    public String encode(String text) {
        log.info("Input: {}", text);
        String result = text.chars()
                .map(Alphabet::getLetterPosition)
                .map(letterCode -> {
                    int generated = generateKey();
                    return letterCode ^ generated;
                })
                .map(Alphabet::getLetterByPosition)
                .mapToObj(code -> (char) code)
                .map(String::valueOf)
                .collect(Collectors.joining());
        log.info("Result: {}", result);
        return result;
    }

    @Override
    public String decode(String text) {
        return encode(text);
    }

    private int generateKey() {
        int result = 0;
        for (int i = 0; i < 5; i++) {
            int x8 = getBitAt(xRegister, 8);
            int y10 = getBitAt(yRegister, 10);
            int z10 = getBitAt(zRegister, 10);
            int majority = majority(x8, y10, z10);
            if (x8 == majority)
                stepX();
            if (y10 == majority)
                stepY();
            if (z10 == majority)
                stepZ();
            result <<= 1;
            result |=
                    getBitAt(xRegister, 18) ^
                            getBitAt(yRegister, 21) ^
                            getBitAt(zRegister, 22);
        }
        return result;
    }


    private void stepX() {
        int x13 = getBitAt(xRegister, 13);
        int x16 = getBitAt(xRegister, 16);
        int x17 = getBitAt(xRegister, 17);
        int x18 = getBitAt(xRegister, 18);
        int result = x13 ^ x16 ^ x17 ^ x18;
        xRegister = transformAndGet(xRegister, result);
    }

    private void stepY() {
        int y20 = getBitAt(yRegister, 20);
        int y21 = getBitAt(yRegister, 21);
        int result = y20 ^ y21;
        yRegister = transformAndGet(yRegister, result);
    }

    private void stepZ() {
        int z7 = getBitAt(zRegister, 7);
        int z20 = getBitAt(zRegister, 20);
        int z21 = getBitAt(zRegister, 21);
        int z22 = getBitAt(zRegister, 22);
        int result = z7 ^ z20 ^ z21 ^ z22;
        zRegister = transformAndGet(zRegister, result);
    }

    private int transformAndGet(int register, int lastByte) {
        register <<= 1;
        register |= lastByte;
        return register;
    }

    private int majority(int x, int y, int z) {
        int one = 0;
        int zero = 0;
        if (x == 1)
            one++;
        else
            zero++;
        if (y == 1)
            one++;
        else
            zero++;
        if (z == 1)
            one++;
        else
            zero++;
        return one > zero ? 1 : 0;
    }

    private int getBitAt(int value, int position) {
        return (value & (1 << position)) >>> position;
    }

}

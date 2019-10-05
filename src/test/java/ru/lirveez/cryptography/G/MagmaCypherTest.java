package ru.lirveez.cryptography.G;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MagmaCypherTest {

    private MagmaCypher magmaCypher = new MagmaCypher();

    @Test
    public void shouldEncode() {
        String asdА = "asdА";
        init();
        String result = magmaCypher.encode(asdА);
        init();
        String decode = magmaCypher.decode(result);
        assertEquals(asdА, decode);
    }

    public void init() {
        int[] keySplit = {
                0b1110011101101110011111011101101,
                0b1110110111110111001111011101,
                0b11011101010111100111100111011,
                0b1100011011100110110101101101111,
                0b110001111100111010111010111111,
                0b110011011100111011011101011111,
                0b1111111111111111111111111,
                0b1101010110010010011111
        };
        magmaCypher.setC1(0b11100100011000011111111);
        magmaCypher.setC2(0b1111111010101100010011010100000);
        magmaCypher.setKey(keySplit);
        magmaCypher.setSynch(0b11111111111110110011011110000111010010101100010010010010000111L);
    }
}
package ru.lirveez.cryptography.G;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class MagmaCipherTest {

    private MagmaCipher magmaCipher;

    @Test
    public void properCyclicShift() {
        magmaCipher = new MagmaCipher(BigInteger.ONE);

        val result = magmaCipher.cyclicLeftShift(new BigInteger("11111111111000000000000000000000", 2));

        assertEquals(new BigInteger("11111111111", 2), result);
    }

    @Test
    @SneakyThrows
    public void properSplitKey() {
        magmaCipher = new MagmaCipher(new BigInteger("101010101010101010100111110101010101011111111111111000010101", 2));

        val splitKeysField = MagmaCipher.class.getDeclaredField("splitKeys");
        splitKeysField.setAccessible(true);
        val splitKeys = (BigInteger[]) splitKeysField.get(magmaCipher);

        assertEquals(new BigInteger("01010101011111111111111000010101", 2), splitKeys[7]);
        assertEquals(new BigInteger("1010101010101010101001111101", 2), splitKeys[6]);
        assertEquals(BigInteger.ZERO, splitKeys[5]);
        assertEquals(BigInteger.ZERO, splitKeys[4]);
        assertEquals(BigInteger.ZERO, splitKeys[3]);
        assertEquals(BigInteger.ZERO, splitKeys[2]);
        assertEquals(BigInteger.ZERO, splitKeys[1]);
        assertEquals(BigInteger.ZERO, splitKeys[0]);
    }
}
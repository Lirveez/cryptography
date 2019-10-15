package ru.lirveez.cryptography.E;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;


class VernamEncoderTest {

    private VernamEncoder vernamEncoder = new VernamEncoder("АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ", BigInteger.ONE);

    @Test
    void shouldReturnProperBase() {
        assertEquals(13, vernamEncoder.log2(new BigInteger("10000000000000", 2).intValue()));
    }

    @Test
    void shouldReturnNegativeOneIfNotLogarithmOf2() {
        assertEquals(-1, vernamEncoder.log2(new BigInteger("11010", 2).intValue()));
    }

    @Test
    void shouldProperlyEncode() {
        vernamEncoder = new VernamEncoder("ABCD", new BigInteger("10100110", 2));

        var result = vernamEncoder.encode("ADBC");

        assertEquals("CBAA", result);
    }

    @Test
    void shouldProperlyDecode() {
        vernamEncoder = new VernamEncoder("ABCD", new BigInteger("10100110", 2));

        var result = vernamEncoder.decode("CBAA");

        assertEquals("ADBC", result);
    }

    @Test(expected = IllegalArgumentException.class)
    void shouldThrowExceptionIfTextAndKeyLengthAreDifferent() {
        new VernamEncoder("AB", BigInteger.TEN).encode("A");
    }
}
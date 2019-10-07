package ru.lirveez.cryptography.H;

import lombok.val;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class RsaEncoderTest {

    @Test
    public void shouldProperlyEncode() {
        val key = new Key(BigInteger.valueOf(3L), BigInteger.valueOf(9173503L), BigInteger.valueOf(6111579L));

        val encoded = new RsaEncoder(key).encode(BigInteger.valueOf(111111L));

        assertEquals(BigInteger.valueOf(4051753L), encoded);
    }

    @Test
    public void shouldEncodeAndDecode() {
        val key = new Key(BigInteger.valueOf(3L), BigInteger.valueOf(9173503L), BigInteger.valueOf(6111579L));
        val rsaEncoder = new RsaEncoder(key);
        val text = BigInteger.valueOf(112312L);

        assertEquals(text, rsaEncoder.decode(rsaEncoder.encode(text)));
    }
}
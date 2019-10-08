package ru.lirveez.cryptography.H;

import lombok.val;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;


// Values were taken from: https://ru.wikipedia.org/wiki/RSA#%D0%9A%D0%BE%D1%80%D1%80%D0%B5%D0%BA%D1%82%D0%BD%D0%BE%D1%81%D1%82%D1%8C_%D1%81%D1%85%D0%B5%D0%BC%D1%8B_RSA
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
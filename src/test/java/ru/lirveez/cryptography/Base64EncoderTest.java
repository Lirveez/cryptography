package ru.lirveez.cryptography;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class Base64EncoderTest {

    private final String decoded = "some text for testing";
    private final String encoded = "c29tZSB0ZXh0IGZvciB0ZXN0aW5n";

    @Test
    public void shouldProperlyEncode() {
        assertEquals(encoded, Base64.getEncoder().encodeToString(decoded.getBytes()));
    }

    @Test
    public void shouldProperlyDecode() {
        assertEquals(decoded, new String(Base64.getDecoder().decode(encoded)));
    }
}

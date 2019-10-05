package ru.lirveez.cryptography.E;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShannonCypherTest {

    private ShannonCypher shannonCypher= new ShannonCypher();

    @Test
    public void shouldEncode() {
        String result = shannonCypher.encode("привет");

        assertEquals("привет", shannonCypher.decode(result));
    }

    @Test
    public void shouldDecode() {
        String result = shannonCypher.decode("QPHBGR");

        assertEquals("привет", result);
    }
}
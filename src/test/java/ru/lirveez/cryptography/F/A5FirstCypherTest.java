package ru.lirveez.cryptography.F;

import org.junit.Test;

import static org.junit.Assert.*;

public class A5FirstCypherTest {

    private A5FirstCypher a5FirstCypher = new A5FirstCypher();

    @Test
    public void shouldEncode() {
        var encode = a5FirstCypher.encode("привет");

        assertEquals("жпЯлыь", encode);
    }

    @Test
    public void shouldDecode() {
        var encode = a5FirstCypher.decode("жпЯлыь");

        assertEquals("привет", encode);
    }
}
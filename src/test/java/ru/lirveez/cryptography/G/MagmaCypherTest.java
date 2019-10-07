package ru.lirveez.cryptography.G;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MagmaCypherTest {

    private MagmaCypher magmaCypher = new MagmaCypher();

    @Test
    public void shouldEncode() {
        String text = "Привет! Как твои дела?";
        magmaCypher.setSynch(0b11111111111110110011011110000111010010101100010010010010000111L);
        String result = magmaCypher.encode(text);
        magmaCypher.setSynch(0b11111111111110110011011110000111010010101100010010010010000111L);
        String decode = magmaCypher.decode(result);
        assertEquals(text.trim(), decode.trim());
    }
}
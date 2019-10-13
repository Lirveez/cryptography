package ru.lirveez.cryptography.A;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class CaesarCypherTest {

    private int OFFSET = 3;
    private CaesarCypher caesarCypher = new CaesarCypher(OFFSET);

    @Test
    public void shouldEncode() {
        var text = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
        log.info("Encoding: {}", text);
        log.info("Offset: {}", OFFSET);

        var encoded = caesarCypher.encode(text);

        log.info("Result: {}", encoded);
        assertEquals("гдежзийклмнопрстуфхцчшщъыьэюяабв", encoded);
    }

    @Test
    public void shouldDecode() {
        var text = "гдежзийклмнопрстуфхцчшщъыьэюяабв";
        log.info("Decoding: {}", text);
        log.info("Offset: {}", OFFSET);

        var decoded = caesarCypher.decode(text);
        log.info("Result: {}", decoded);

        assertEquals("абвгдежзийклмнопрстуфхцчшщъыьэюя", decoded);
    }
}
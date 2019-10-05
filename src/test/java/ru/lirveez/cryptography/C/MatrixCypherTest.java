package ru.lirveez.cryptography.C;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class MatrixCypherTest {

    private MatrixCypher matrixCypher = new MatrixCypher();

    @Test
    public void shouldEncodeText() {
        var text = "ЗАБАВА";
        int i = 1 % 7;
        var result = matrixCypher.encode(text);
        log.info("Encoding: {}", text);
        log.info("Result: {}", result);
        assertEquals("28,35,67,21,26,38", result);
    }

    @Test
    public void shouldDecodeText() {
        var encodedString = "156,185,294,179,89,167";

        var result = matrixCypher.decode(encodedString);

        log.info("Decoding: <{}>", encodedString);
        log.info("Encoding: {}", result);

        assertEquals("ПРИВЕТ", result);
    }
}

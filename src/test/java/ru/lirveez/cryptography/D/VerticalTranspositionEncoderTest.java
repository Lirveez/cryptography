package ru.lirveez.cryptography.D;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


@Slf4j
class VerticalTranspositionEncoderTest {

    private VerticalTranspositionEncoder encoder;

    private VerticalTranspositionEncoder withKey(Integer... key) {
        return new VerticalTranspositionEncoder(key);
    }

    @Test
    void shouldEncode() {
        encoder = withKey(3, 1, 4, 2, 5);
        val text = "пример маршрутной перестановки";

        val result = encoder.encode(text);

        val expected = "иатеаипррйсвмрнрнрмупткешоео";
        assertEquals(expected, result);
        log.info("Got input: {}, encoded: {}", text, result);
    }

    @Test
    void shouldDecode() {
        encoder = withKey(3, 1, 4, 2, 5);
        val encoded = "рмупткмрнрнпррйсвиатеаиешоео";

        val result = encoder.decode(encoded);

        assertEquals("примермаршрутнойперестановки", result);
        log.info("Got input: {}, encoded: {}", encoded, result);
    }
}
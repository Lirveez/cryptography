package ru.lirveez.cryptography.A;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import ru.lirveez.cryptography.Encoder;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@Slf4j
class ReplacementEncoderTest {

    private Encoder algorithm;

    @Before
    void setUp() {
        algorithm = new ReplacementEncoder(
                "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".chars().boxed().collect(Collectors.toList())
        );
    }

    @Test
    void shouldProperlyEncodeCyrillic() {
        val input = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        val result = algorithm.encode(input);

        assertEquals("ЯЮЭЬЫЪЩШЧЦХФУТСРПОНМЛКЙИЗЖЕДГВБА", result);
        log.info("Got input: {}, encoded: {}", input, result);
    }

    @Test
    void shouldProperlyDecodeCyrillic() {
        val input = "ОФСЭС";

        val result = algorithm.decode(input);

        assertEquals("СЛОВО", result);
        log.info("Got input: {}, encoded: {}", input, result);
    }
}
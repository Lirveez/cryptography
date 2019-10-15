package ru.lirveez.cryptography.B;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


@Slf4j
class VigenereEncoderTest {

    private VigenereEncoder withKey(String key) {
        val alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ".chars()
                .boxed()
                .collect(Collectors.toList());
        return new VigenereEncoder(alphabet, key);
    }

    @Test
    void shouldProperlyEncode() {
        val input = "АБВГД";
        val encoder = withKey("МАМА");

        val result = encoder.encode(input);

        assertEquals("МБОГР", result);
        log.info("Got input: {}, encoded: {}", input, result);
    }
}
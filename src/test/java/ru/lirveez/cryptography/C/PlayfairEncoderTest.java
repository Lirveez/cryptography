package ru.lirveez.cryptography.C;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
class PlayfairEncoderTest {

    private PlayfairEncoder encoder;

    @Test
    void shouldEncode() {
        encoder = withKey("WHEATSON");
        val text = "IDIOCY OFTEN LOOKS LIKE INTELLIGENCE";

        val result = encoder.encode(text);

        val expected = "KFFBBZFMWASPNVCFDUKDAGCEWPQDPNBSNE";
        assertEquals(expected, result);
        log.info("Got input: {}, encoded: {}", text, result);
    }

    @Test
    void shouldDecode() {
        encoder = withKey("WHEATSON");
        val text = "KFFBBZFMWASPNVCFDUKDAGCEWPQDPNBSNE";

        val result = encoder.decode(text);

        assertEquals("IDIOCYOFTENLOXOKSLIKEINTELLIGENCEX", result);
        log.info("Got input: {}, encoded: {}", text, result);
    }

    private PlayfairEncoder withKey(String key) {
        return new PlayfairEncoder(key);
    }

    @Test
    void shouldSplitIntoPairsEvenLength() {
        assertEquals("AB CD", withKey("a").getPaired("ABCD"));
    }

    @Test
    void shouldSplitIntoPairsOddLength() {
        assertEquals("AB C", withKey("a").getPaired("ABC"));
    }
}
package ru.lirveez.cryptography.F;

import org.junit.Test;

import java.beans.Encoder;

import static org.junit.Assert.assertEquals;
import static ru.lirveez.cryptography.F.AFiveEncoder.getBitAt;

class AFiveEncoderTest extends Encoder {

    @Test
    void shouldReturnProperBit() {
        int value = 0b010110;
        assertEquals(0, getBitAt(value, 0));
        assertEquals(1, getBitAt(value, 1));
        assertEquals(1, getBitAt(value, 2));
        assertEquals(0, getBitAt(value, 3));
        assertEquals(1, getBitAt(value, 4));
        assertEquals(0, getBitAt(value, 5));
    }
}
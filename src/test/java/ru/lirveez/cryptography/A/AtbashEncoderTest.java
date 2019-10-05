package ru.lirveez.cryptography.A;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtbashEncoderTest {

    private AtbashCypher atbashCypher = new AtbashCypher();

    @Test
    public void shouldReturnEncodedString() {
        String result = atbashCypher.encode("абвгдААЯ");

        assertEquals("яюэьыЯЯА", result);
    }

    @Test
    public void shouldDoNothingForNonAlphabeticalCharacters() {
        String source = "!>?!@#ABCDEE";

        String encoded = atbashCypher.encode(source);

        assertEquals(source, encoded);
    }

    @Test
    public void shouldReturnSameString() {
        String source = "Привет, как дела?";

        String encoded = atbashCypher.encode(source);
        String decoded = atbashCypher.decode(encoded);

        assertEquals(source, decoded);
    }
}

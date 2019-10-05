package ru.lirveez.cryptography.D;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;


public class VerticalTranspositionTest {

    private VerticalTransposition verticalTransposition = new VerticalTransposition();


    @Test
    public void shouldEncode() {
        var result = verticalTransposition.encode("пример маршрутной перестановки");

        assertEquals("ешрнмреопноимастртйкрреаиупв", result);
    }

    @Test
    public void shouldDecode() {
        var result = verticalTransposition.decode("ешрнмреопноимастртйкрреаиупв");

        assertEquals("примермаршрутнойперестановки", result);
    }
}
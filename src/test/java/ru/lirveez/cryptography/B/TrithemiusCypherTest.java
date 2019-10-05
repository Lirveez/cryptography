package ru.lirveez.cryptography.B;

import org.junit.Test;

public class TrithemiusCypherTest {

    private TrithemiusCypher trithemiusCypher = new TrithemiusCypher();

    @Test
    public void name() {
        String result = trithemiusCypher.encode("аааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааа");
        System.out.println(result);
    }
}

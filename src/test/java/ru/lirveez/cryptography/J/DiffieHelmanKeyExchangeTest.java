package ru.lirveez.cryptography.J;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.*;

@Slf4j
public class DiffieHelmanKeyExchangeTest {

    private DiffieHelmanKeyExchange exchange = new DiffieHelmanKeyExchange();

    private Person personA = new Person();
    private Person personB = new Person();

    @Before
    public void setUp() throws Exception {
        personA.setPrivateKey(BigInteger.valueOf(199));
        personB.setPrivateKey(BigInteger.valueOf(157));
        personA.setPublicKey(BigInteger.valueOf(197));
        personB.setPublicKey(BigInteger.valueOf(151));
    }

    @Test
    public void shouldDoKeyExchange() {
        exchange.initKeyExchange(personA, personB);

        assertNotNull(personA.getComplexKey());
        assertNotNull(personB.getComplexKey());
        assertEquals(personA.getComplexKey(), personB.getComplexKey());
    }

    @Test
    public void shouldEncodeAndDecode() {
        exchange.initKeyExchange(personA, personB);

        var messageA = "Привет, как дела?";
        log.info("Message A: {}", messageA);

        var personAEncodedText = personA.encode(messageA);
        log.info("Person A sending: {}", personAEncodedText);
        var personBDecodedText = personB.encode(personAEncodedText);
        log.info("Person B decoded: {}", personBDecodedText);

        assertEquals(messageA.trim(), personBDecodedText.trim());

        var messageB = "Хорошо, а как твои?";
        log.info("Message B: {}", messageB);
        var personBEncodedText = personB.encode(messageB);
        log.info("Person B sending: {}", personBEncodedText);
        var personADecodedText = personA.encode(personBEncodedText);
        log.info("Person A decoded: {}", personADecodedText);

        assertEquals(messageB.trim(), personADecodedText.trim());
    }
}
package ru.lirveez.cryptography.G;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.math.BigInteger;
import java.util.Base64;
import java.util.BitSet;
import java.util.function.UnaryOperator;

@Slf4j
public class Launcher {

    public static final String TEXT = "Super secret text";
    public static final UnaryOperator<String> base64encoder = s -> Base64.getEncoder().encodeToString(s.getBytes());

    public static void main(String[] args) {
        val magmaCipher = new MagmaCipher(new BigInteger("101010101010101010100111110101010101011111111111111000010101", 2));
        val encoded = magmaCipher.encode(new BigInteger("1000000000000000000000000000000000000000000000000000000000000001", 2));
        val decoded = magmaCipher.decode(encoded);
        log.info("Encoded to: {}", encoded.toString(2));
        log.info("Decoded to: {}", decoded.toString(2));


        MagmaCypher magmaCypher = new MagmaCypher();
        System.out.printf("From \r\n'%s'\r\n to \r\n'%s'\r\n", TEXT, base64encoder.apply(magmaCypher.encode(TEXT)));
    }
}

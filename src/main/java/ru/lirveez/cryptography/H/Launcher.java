package ru.lirveez.cryptography.H;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.math.BigInteger;
import java.nio.charset.Charset;

import static ru.lirveez.cryptography.BigIntegerUtil.fromBigInt;
import static ru.lirveez.cryptography.BigIntegerUtil.rawText;

@Slf4j
public class Launcher {

    public static final String TEXT = "Какой-то секретный текст";

    public static void main(String[] args) {
        val key = new KeyGenerator().generateKey(1024);
        log.info("Key is {}", key);
        byte[] bytedText = TEXT.getBytes(Charset.defaultCharset());
        log.info("TEXT: {}", bytedText);


        val binaryText = rawText(TEXT);
        val rsaEncoder = new RsaEncoder(key);
        val encoded = rsaEncoder.encode(binaryText);
        log.info("Byted text: {}", binaryText.toString(2));
        log.info("Encoded: {}", encoded.toString(2));
        val decoded = rsaEncoder.decode(encoded);
        log.info("Decoded: {}", decoded.toString(2));
        val str = fromBigInt(decoded);
        log.info("String is {}", str);
    }
}

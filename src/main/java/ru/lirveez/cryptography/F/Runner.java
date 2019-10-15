package ru.lirveez.cryptography.F;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class Runner {

    public static void main(String[] args) {
        val encoder = new AFiveEncoder("АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ",
                -1L
        );
        val encoded = encoder.encode("Зашифрованнаястрока".toUpperCase());
        log.info("Encoded: {}", encoded);
        log.info("Decoded: {}", encoder.decode(encoded));
    }
}
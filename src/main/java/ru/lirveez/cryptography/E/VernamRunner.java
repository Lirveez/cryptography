package ru.lirveez.cryptography.E;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class VernamRunner {

    public static void main(String[] args) {
        val encoder = new VernamEncoder("АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЬЭЮЯ ");
        val text = "ТЕКСТ ДЛЯ ШИФРОВКИ";
        val encoded = encoder.encodeWithRandomKey(text);
        log.info("Исходный: '{}', зашифрованный: '{}'", text, encoded);
        log.info("После расшифровки: '{}'", encoder.decodeWithKey(encoded));

    }
}

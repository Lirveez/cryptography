package ru.lirveez.cryptography.H;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class Launcher {

    public static void main(String[] args) {
        val key = new KeyGenerator().generateKey(10);
        log.info("Key is {}", key);
    }
}

package ru.lirveez.cryptography;

public interface Encoder {
    //Шифровать
    String encode(String input);
    //Расшифровать
    String decode(String input);
}

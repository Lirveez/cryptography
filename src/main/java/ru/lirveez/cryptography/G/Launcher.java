package ru.lirveez.cryptography.G;

import java.util.Base64;
import java.util.function.UnaryOperator;

public class Launcher {

    public static final String TEXT = "Super secret text";
    public static final UnaryOperator<String> base64encoder = s -> Base64.getEncoder().encodeToString(s.getBytes());

    public static void main(String[] args) {
        System.out.printf("From \r\n'%s'\r\n to \r\n'%s'\r\n", TEXT, base64encoder.apply(new MagmaCypher().encode(TEXT)));
    }
}

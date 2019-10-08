package ru.lirveez.cryptography.H;

import lombok.NoArgsConstructor;
import lombok.val;

import java.math.BigInteger;
import java.util.Random;

@NoArgsConstructor
public class KeyGenerator {

    private static final BigInteger[] FERMAT_NUMBER = new BigInteger[]{
            BigInteger.valueOf(17),
            BigInteger.valueOf(257),
            BigInteger.valueOf(65537L)
    };

    public Key generateKey(int bitLength) {
        val p = BigInteger.probablePrime(bitLength, new Random());
        val q = BigInteger.probablePrime(bitLength, new Random());
        return generateFor(p, q);
    }

    public Key generateFor(BigInteger q, BigInteger p) {
        val n = p.multiply(q);
        val eulerFunction = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        val openExponent = FERMAT_NUMBER[new Random().nextInt(FERMAT_NUMBER.length)];
        val d = openExponent.modInverse(eulerFunction);
        return new Key(openExponent, n, d);
    }
}

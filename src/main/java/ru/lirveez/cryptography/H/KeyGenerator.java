package ru.lirveez.cryptography.H;

import lombok.NoArgsConstructor;
import lombok.val;

import java.math.BigInteger;
import java.util.Random;

@NoArgsConstructor
public class KeyGenerator {

    public Key generateKey(int bitLength) {
        val p = BigInteger.probablePrime(bitLength, new Random());
        val q = BigInteger.probablePrime(bitLength, new Random());
        return generateFor(p, q);
    }

    public Key generateFor(BigInteger q, BigInteger p) {
        val n = p.multiply(q);
        val eulerFunction = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        val openExponent = BigInteger.valueOf(65537L); // TODO: 07.10.2019 Generate
        val d = openExponent.modInverse(eulerFunction);
        return new Key(openExponent, n, d);
    }

}

package ru.lirveez.cryptography.H;

import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@RequiredArgsConstructor
public class RsaEncoder {

    private final Key key;

    public RsaEncoder(int bitLength) {
        this.key = new KeyGenerator().generateKey(bitLength);
    }

    public BigInteger encode(BigInteger number) {
        return number.modPow(key.getE(), key.getN());
    }

    public BigInteger decode(BigInteger number) {
        return number.modPow(key.getD(), key.getN());
    }
}

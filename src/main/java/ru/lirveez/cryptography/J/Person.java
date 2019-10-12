package ru.lirveez.cryptography.J;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import static ru.lirveez.cryptography.BigIntegerUtil.fromBigInt;
import static ru.lirveez.cryptography.BigIntegerUtil.rawText;

@Data
@NoArgsConstructor
public class Person {
    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger complexKey;

    public BigInteger generateOwnBasedPartialKey(BigInteger strangePublicKey){
        return publicKey.modPow(privateKey, strangePublicKey);
    }

    public BigInteger generateStrangeBasedPartialKey(BigInteger strangePublicKey){
        return strangePublicKey.modPow(privateKey, publicKey);
    }


    public BigInteger generateOwnBasedComplexKey(BigInteger partialKey, BigInteger publicKey){
        complexKey = partialKey.modPow(privateKey, publicKey);
        return complexKey;
    }
    public BigInteger generateStrangeBasedComplexKey(BigInteger partialKey){
        complexKey = partialKey.modPow(privateKey, publicKey);
        return complexKey;
    }


    public String encode(String text){
        BigInteger bigIntText = rawText(text).xor(complexKey);
        return fromBigInt(bigIntText);
    }
}

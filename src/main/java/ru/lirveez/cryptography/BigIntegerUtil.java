package ru.lirveez.cryptography;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.math.BigInteger;
import java.nio.charset.Charset;

@UtilityClass
public class BigIntegerUtil {

    public static BigInteger rawText(String text) {
        val bytes = text.getBytes(Charset.defaultCharset());
        var bi = BigInteger.ZERO;
        for (val b : bytes) {
            val temp = BigInteger.valueOf(Byte.toUnsignedLong(b));
            bi = bi.shiftLeft(8).or(temp);
        }
        return bi;
    }
    public static String fromBigInt(BigInteger bi) {
        return new String(bi.toByteArray(), Charset.defaultCharset());
    }
}

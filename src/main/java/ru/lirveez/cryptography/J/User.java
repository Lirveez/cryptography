package ru.lirveez.cryptography.J;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Data
@RequiredArgsConstructor
public class User {
    private final BigInteger a, n;

    private BigInteger k, y, partnerY, commonKey;
}

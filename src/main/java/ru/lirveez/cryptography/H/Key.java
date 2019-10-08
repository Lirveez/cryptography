package ru.lirveez.cryptography.H;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.math.BigInteger;

@Value
public class Key {

    BigInteger e, n, d;

}

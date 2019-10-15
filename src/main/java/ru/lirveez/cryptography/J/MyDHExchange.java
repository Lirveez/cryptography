package ru.lirveez.cryptography.J;

import java.math.BigInteger;
import java.util.Random;

public class MyDHExchange {

    public void doExchange(User user1, User user2) {
        user1.setK(new BigInteger(64, new Random()));
        user2.setK(new BigInteger(64, new Random()));

        user1.setY(user1.getA().modPow(user1.getK(), user1.getN()));
        user2.setY(user2.getA().modPow(user2.getK(), user2.getN()));

        user1.setPartnerY(user2.getY());
        user2.setPartnerY(user1.getY());

        user1.setCommonKey(user1.getPartnerY().modPow(user1.getK(), user1.getN()));
        user2.setCommonKey(user2.getPartnerY().modPow(user2.getK(), user2.getN()));
    }
}

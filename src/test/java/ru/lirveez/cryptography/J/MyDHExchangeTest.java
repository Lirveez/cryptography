package ru.lirveez.cryptography.J;

import lombok.val;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class MyDHExchangeTest {


    private MyDHExchange exchange = new MyDHExchange();

    @Test
    public void commonKeyShouldBeEqual() {
        val a = new BigInteger("999999999999921391239129319239129319239129392139219319");
        val n = new BigInteger("23874872358634286543268548534874585468438786548765487654386345");
        val user1 = new User(a, n);
        val user2 = new User(a, n);

        exchange.doExchange(user1, user2);

        assertEquals(user1.getCommonKey(), user2.getCommonKey());
        System.out.println("User 1 common key: " + user1.getCommonKey().toString());
        System.out.println("User 2 common key: " + user1.getCommonKey().toString());
    }
}
package ru.lirveez.cryptography.J;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
public class DiffieHelmanKeyExchange {

    public void initKeyExchange(Person personA, Person personB){
        var encoder = Base64.getEncoder();

        var personBPublicKey = personB.getPublicKey();
        log.info("Person B sending public key: {}", encoder.encodeToString(personBPublicKey.toByteArray()));
        var partialKeyA = personA.generateOwnBasedPartialKey(personBPublicKey);
        var personAPublicKey = personA.getPublicKey();
        log.info("Person A sending public key: {}", encoder.encodeToString(personAPublicKey.toByteArray()));
        var partialKeyB = personB.generateStrangeBasedPartialKey(personAPublicKey);

        var keyA = personA.generateOwnBasedComplexKey(partialKeyB, personBPublicKey);
        log.info("Person A generated secret key: {}", encoder.encodeToString(keyA.toByteArray()));
        var keyB = personB.generateStrangeBasedComplexKey(partialKeyA);
        log.info("Person B generated secret key: {}", encoder.encodeToString(keyB.toByteArray()));

    }
}

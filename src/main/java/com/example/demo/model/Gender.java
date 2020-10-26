package com.example.demo.model;

import java.security.SecureRandom;

import static java.util.List.of;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender getRandom() {
        SecureRandom random = new SecureRandom();
        return of(values()).get(random.nextInt(of(values()).size()));
    }

}

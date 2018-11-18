package org.ran.jsonschema.suppliers;

import java.util.Random;

public class RandomNumberSupplier {
    public RandomNumberSupplier() {
    }

    int getRandomInteger(int max, int min) {
        Random random = new Random();
        int randomInt = random.nextInt(max + 1 - min) + min;
        return randomInt;
    }
}
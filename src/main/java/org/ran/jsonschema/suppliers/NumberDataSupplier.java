package org.ran.jsonschema.suppliers;

import org.everit.json.schema.NumberSchema;

import java.util.Optional;
import java.util.Random;

public class NumberDataSupplier extends AbstractDataSupplier<NumberSchema, Number> {

    private final RandomNumberSupplier randomNumberSupplier = new RandomNumberSupplier();

    @Override
    public Number generateDataFromSchema(NumberSchema schema) {
        Number max = Optional.ofNullable(schema.getMaximum()).orElse(100);
        Number min = Optional.ofNullable(schema.getMinimum()).orElse(1);
        if (schema.requiresInteger()) {
            return randomNumberSupplier.getRandomInteger(max.intValue(), min.intValue());
        } else {
            return getRandomNumber(max.doubleValue(), min.doubleValue());
        }

    }

    private Double getRandomNumber(double rangeMax, double rangeMin) {
        Random random = new Random();
        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();

    }

}

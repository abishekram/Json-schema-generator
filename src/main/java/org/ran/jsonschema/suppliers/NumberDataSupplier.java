package org.ran.jsonschema.suppliers;

import org.everit.json.schema.NumberSchema;

import java.util.Optional;

import static org.ran.jsonschema.RandomUtils.getRandomInteger;
import static org.ran.jsonschema.RandomUtils.getRandomNumber;

public class NumberDataSupplier extends AbstractDataSupplier<NumberSchema, Number> {

    @Override
    public Number generateDataFromSchema(NumberSchema schema) {

        Number max = Optional.ofNullable(schema.getMaximum()).orElse(100);
        if (schema.isExclusiveMaximum()) {
            max = max.doubleValue() - 1;
        }

        Number min = Optional.ofNullable(schema.getMinimum()).orElse(1);
        if (schema.isExclusiveMinimum()) {
            min = min.doubleValue() + 1;
        }

        if (schema.requiresInteger()) {
            Number multipleOf = Optional.ofNullable(schema.getMultipleOf()).orElse(1);
            return getRandomInteger(max.intValue(), min.intValue(), multipleOf.intValue());
        } else {
            Number multipleOf = schema.getMultipleOf();
            if (multipleOf == null) {
                return getRandomNumber(max.doubleValue(), min.doubleValue());
            } else {
                return getRandomNumber(max.doubleValue(), min.doubleValue(), multipleOf.doubleValue());
            }
        }
    }
}

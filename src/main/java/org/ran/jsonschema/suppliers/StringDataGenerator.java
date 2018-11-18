package org.ran.jsonschema.suppliers;

import org.everit.json.schema.StringSchema;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class StringDataGenerator extends AbstractDataSupplier<StringSchema, String> {

    @Override
    public String generateDataFromSchema(StringSchema schema) {
        int min = Optional.ofNullable(schema.getMinLength()).orElse(1);
        int max = Optional.ofNullable(schema.getMaxLength()).orElse(10);
        Random random = new Random();
        int maxDerived = Math.max(random.nextInt(max) - min, 1);
        return getRandomString(maxDerived);

    }

    private String getRandomString(int maxDerived) {

        Random random = new Random();
        Stream<String> randomStrings =
                Stream.generate(
                        () ->
                                random
                                        .ints('a', 'z')
                                        .limit(maxDerived)
                                        .collect(
                                                StringBuilder::new,
                                                (builder, codePoint) -> builder.appendCodePoint(codePoint),
                                                StringBuilder::append)
                                        .toString());
        return randomStrings.findFirst().get();

    }
}

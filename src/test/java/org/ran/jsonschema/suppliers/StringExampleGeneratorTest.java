package org.ran.jsonschema.suppliers;

import org.everit.json.schema.StringSchema;
import org.junit.jupiter.api.Test;

class StringExampleGeneratorTest {
    StringDataGenerator generator = new StringDataGenerator();

    @Test
    public void generateRandom() {
        System.out.println(generator.forSchema(new StringSchema.Builder().build()));
    }
}
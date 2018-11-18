package org.ran.jsonschema.suppliers;

import org.everit.json.schema.NumberSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberDataSupplierTest {

    @Test
    void generateFromSchema() {
        int min = 2;
        int max = 10;
        Number asd = new NumberDataSupplier().forSchema(NumberSchema.builder().maximum(max).minimum(min).requiresInteger(true).build());
        int outPut = asd.intValue();
        assertTrue(outPut <= max && outPut >= min);
    }

    @Test
    void generateFromSchemaNumber() {
        double min = 234.2;
        double max = 2434.32;
        Number generatedData =
                new NumberDataSupplier().forSchema(NumberSchema.builder().maximum(max).minimum(min).requiresInteger(false).build());
        double outputValue = generatedData.doubleValue();
        assertTrue(outputValue <= max && outputValue >= min);
    }

    @Test
    void generateFromSchemaWithoutMin() {
        Number generatedData =
                new NumberDataSupplier().forSchema(NumberSchema.builder().requiresInteger(false).build());
    }
}
package org.ran.jsonschema.suppliers;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ran.jsonschema.SchemaDataSupplier;

import java.io.IOException;
import java.io.InputStream;

class ObjectDataSupplierTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateFromSchema() {
        try (InputStream inputStream = getClass().getResourceAsStream("schema.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            System.out.println(new SchemaDataSupplier().get(schema));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
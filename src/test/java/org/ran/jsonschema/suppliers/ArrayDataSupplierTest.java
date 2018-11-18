package org.ran.jsonschema.suppliers;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;
import org.ran.jsonschema.SchemaDataSupplier;

import java.io.IOException;
import java.io.InputStream;

class ArrayDataSupplierTest {

    @Test
    void generateFromSchema() {
        ArrayDataSupplier supplier = new ArrayDataSupplier();
        try (InputStream inputStream = getClass().getResourceAsStream("jsonArraySchema.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            String as = String.valueOf(supplier.forSchema(schema));
            System.out.println(as);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void generateFromObjectSchema() {

        try (InputStream inputStream = getClass().getResourceAsStream("jsonArraySchemaWithObject.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            String as = String.valueOf(new SchemaDataSupplier().get(schema));
            System.out.println(as);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
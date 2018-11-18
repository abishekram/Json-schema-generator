package org.ran.jsonschema.suppliers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;
import org.ran.jsonschema.SchemaDataSupplier;

import java.io.IOException;
import java.io.InputStream;

public class JacksonJsonTest {

    @Test
    public void testJackosnString() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("jsonArraySchemaWithObject.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            Object generatedData = new SchemaDataSupplier().get(schema);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generatedData);
            System.out.println(json);
            schema.validate(new JSONObject(new JSONTokener(json)));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

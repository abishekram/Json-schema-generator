package org.ran.jsonschema.suppliers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.StringSchema;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.loader.internal.DefaultSchemaClient;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ran.jsonschema.JsonGenerationException;
import org.ran.jsonschema.SchemaDataSupplier;

class StringExampleGeneratorTest {
    StringDataSupplier generator = new StringDataSupplier();

    @Test
    public void generateRandom() {
        System.out.println(generator.forSchema(new StringSchema.Builder().build()));
    }

    @Test
    public void objectWithStringFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"strField\": {\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"strField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMinAndMaxStringFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"strField\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"maxLength\": 10,\n" +
                "      \"minLength\": 1\n" +
                "    }\n" +
                "  },\n" +
                "  \"required\": [\"strField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMinAndMaxStringFieldOneValueGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"strField\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"maxLength\": 3,\n" +
                "      \"minLength\": 3\n" +
                "    }\n" +
                "  },\n" +
                "  \"required\": [\"strField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            System.out.println(json);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithStringFieldCantGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"strField\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"maxLength\": 3,\n" +
                "      \"minLength\": 4,\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"strField\"]\n" +
                "}";

        Assertions.assertThrows(JsonGenerationException.class, () -> {
            generateObjectAsString(schema, mapper);
        });
    }

    private static String generateObjectAsString(String schema, ObjectMapper mapper) throws JsonProcessingException {
        Object generatedData = new SchemaDataSupplier().get(schema);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generatedData);
    }

    private void validateJsonBySchema(String json, String schema) {
        new SchemaLoader.SchemaLoaderBuilder()
                .httpClient(new DefaultSchemaClient())
                .schemaJson(new JSONObject(new JSONTokener(schema)))
                .build()
                .load()
                .build()
                .validate(new JSONObject(new JSONTokener(json)));
    }

    private ObjectMapper mapper = new ObjectMapper();
}
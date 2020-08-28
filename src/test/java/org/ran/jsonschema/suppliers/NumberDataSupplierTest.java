package org.ran.jsonschema.suppliers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.NumberSchema;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.loader.internal.DefaultSchemaClient;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.ran.jsonschema.JsonGenerationException;
import org.ran.jsonschema.SchemaDataSupplier;

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

    @Test
    public void objectWithIntegerFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMinAndMaxIntegerFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 99,\n" +
                "      \"minimum\": 90\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMinAndMaxAndExclusiveIntegerFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 99,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 90,\n" +
                "      \"exclusiveMinimum\": true\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMultipleOfIntegerFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 99,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 90,\n" +
                "      \"multipleOf\": 3\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMultipleOfIntegerFieldOneValueGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 99,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 96,\n" +
                "      \"multipleOf\": 4\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMultipleOfIntegerFieldOneValueGenerateCorrectly2() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 99,\n" +
                "      \"minimum\": 99,\n" +
                "      \"multipleOf\": 3\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithIntegerFieldCantGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 99,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 96,\n" +
                "      \"multipleOf\": 10\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        Assertions.assertThrows(JsonGenerationException.class, () -> {
            generateObjectAsString(schema, mapper);
        });
    }

    @Test
    public void objectWithIntegerFieldCantGenerateCorrectlyMultipleOf() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 99,\n" +
                "      \"minimum\": 99,\n" +
                "      \"multipleOf\": 2\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        Assertions.assertThrows(JsonGenerationException.class, () -> {
            generateObjectAsString(schema, mapper);
        });
    }

    @Test
    public void objectWithIntegerFieldCantGenerateCorrectlyMaxLowerMin() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 10,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 20,\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        Assertions.assertThrows(JsonGenerationException.class, () -> {
            generateObjectAsString(schema, mapper);
        });
    }

    @Test
    public void objectWithIntegerFieldCantGenerateCorrectlyMaxLowerMin2() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 10,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 10,\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        Assertions.assertThrows(JsonGenerationException.class, () -> {
            generateObjectAsString(schema, mapper);
        });
    }

    @Test
    public void objectWithNumberFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\"\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMinAndMaxNumberFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 99.6,\n" +
                "      \"minimum\": 90.3\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMinAndMaxAndExclusiveNumberFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 99.6,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 90.1,\n" +
                "      \"exclusiveMinimum\": true\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMinAndMaxAndExclusiveNumberFieldOneValueGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 99.6,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 98.6\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMultipleOfNumberFieldGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 99,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 90,\n" +
                "      \"multipleOf\": 3.5\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            System.out.println(json);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMultipleOfNumberFieldOneValueGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 99.7,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 96.4,\n" +
                "      \"multipleOf\": 3.1\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithMultipleOfNumberFieldOneValueGenerateCorrectly2() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 91.8,\n" +
                "      \"minimum\": 91.8,\n" +
                "      \"multipleOf\": 3.4\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        for (int i = 0; i < 100; ++i) {
            String json = generateObjectAsString(schema, mapper);
            validateJsonBySchema(json, schema);
        }
    }

    @Test
    public void objectWithNumberFieldCantGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 99.43,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 96.34,\n" +
                "      \"multipleOf\": 10\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
                "}";

        Assertions.assertThrows(JsonGenerationException.class, () -> {
            generateObjectAsString(schema, mapper);
        });
    }

    @Test
    public void objectWithNumberFieldCantGenerateCorrectlyMultipleOf() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"intField\": {\n" +
                "      \"type\": \"integer\",\n" +
                "      \"maximum\": 99.5,\n" +
                "      \"minimum\": 99.5,\n" +
                "      \"multipleOf\": 2.3\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"intField\"]\n" +
                "}";

        Assertions.assertThrows(JsonGenerationException.class, () -> {
            generateObjectAsString(schema, mapper);
        });
    }

    @Test
    public void objectWithNumberFieldCantGenerateCorrectlyMaxLowerMin() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 10.24,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 20.23,\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
                "}";

        Assertions.assertThrows(JsonGenerationException.class, () -> {
            generateObjectAsString(schema, mapper);
        });
    }

    @Test
    public void objectWithNumberFieldCantGenerateCorrectlyMaxLowerMin2() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"numField\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"maximum\": 10.6,\n" +
                "      \"exclusiveMaximum\": true,\n" +
                "      \"minimum\": 10.6,\n" +
                "    }\n" +
                "  },\n" +
                "    \"required\": [\"numField\"]\n" +
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
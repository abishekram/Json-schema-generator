# Json-schema-generator
Random Data  generator for JSON Schema

Generating Random Data for json schema. Schema parsing based on https://github.com/everit-org/json-schema

For the following json schema. 

```json
{
  "id": "https://example.com/arrays.schema.json",
  "$schema": "http://json-schema.org/draft-07/schema#",
  "description": "A representation of a person, company, organization, or place",
  "type": "object",
  "properties": {
    "fruits": {
      "type": "array",
      "items": {
        "type": "string"
      }
    },
    "valid": {
      "type": "boolean"
    },
    "created": {
      "type": "null"
    },
    "vegetables": {
      "type": "array",
      "items": {
        "$ref": "#/definitions/veggie"
      }
    },
    "color": {
      "enum": [
        "one",
        "two",
        "three"
      ]
    }
  },
  "required": [
    "color",
    "fruits",
    "valid",
    "created",
    "vegetables"
  ],
  "definitions": {
    "veggie": {
      "type": "object",
      "required": [
        "veggieName",
        "veggieLike"
      ],
      "properties": {
        "veggieName": {
          "type": "string",
          "description": "The name of the vegetable."
        },
        "veggieLike": {
          "type": "boolean",
          "description": "Do I like this vegetable?"
        }
      }
    }
  }
}
```

generates
```json
{
  "valid" : true,
  "color" : "two",
  "fruits" : [ "dav", "ipdixu", "aowub" ],
  "created" : null,
  "vegetables" : [ {
    "veggieName" : "irdxlol",
    "veggieLike" : false
  }, {
    "veggieName" : "eh",
    "veggieLike" : false
  }, {
    "veggieName" : "qylwvhj",
    "veggieLike" : false
  } ]
}
```

Example Usage :

```java
  try (InputStream inputStream = getClass().getResourceAsStream("jsonArraySchemaWithObject.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            new SchemaDataSupplier().get(schema);
        } 
```
This only provides java objects as output. Use can use another libraries for jsonification,like

Using Jackson

```java
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("jsonArraySchemaWithObject.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            Object generatedData = new SchemaDataSupplier().get(schema);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generatedData);
            System.out.println(json);
            schema.validate(new JSONObject(new JSONTokener(json)));

        } 
```

Currently following schemas are supported:

1. ObjectSchema
2. StringSchema
3. ArraySchema
4. NumberSchema
5. BooleanSchema
6. EnumSchema
7. ReferenceSchema
8. NullSchema




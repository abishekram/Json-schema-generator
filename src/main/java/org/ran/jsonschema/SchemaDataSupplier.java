package org.ran.jsonschema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SchemaDataSupplier {

    public Object get(Schema schema) {
        return SupplyResolver.resolve(schema).forSchema(schema);
    }

    public Object get(String schemaAsString) {
        Schema schema = SchemaLoader.load(new JSONObject(new JSONTokener(schemaAsString)));
        return SupplyResolver.resolve(schema).forSchema(schema);
    }


}

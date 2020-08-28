package org.ran.jsonschema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.loader.internal.DefaultSchemaClient;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SchemaDataSupplier {

    public Object get(Schema schema) {
        return SupplyResolver.resolve(schema).forSchema(schema);
    }

    public Object get(String schemaAsString) {
        Schema schema = new SchemaLoader.SchemaLoaderBuilder()
                //.draftV7Support()     // uncomment for enable draft-7 support
                .httpClient(new DefaultSchemaClient())
                .schemaJson(new JSONObject(new JSONTokener(schemaAsString)))
                .build()
                .load().build();

        return SupplyResolver.resolve(schema).forSchema(schema);
    }
}

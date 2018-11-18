package org.ran.jsonschema.suppliers;

import org.everit.json.schema.Schema;

public class DummyDataSupplier extends AbstractDataSupplier<Schema, String> {
    @Override
    public String generateDataFromSchema(Schema schema) {
        return "";
    }
}

package org.ran.jsonschema.suppliers;

import org.everit.json.schema.EmptySchema;
import org.everit.json.schema.Schema;


public abstract class AbstractDataSupplier<T extends Schema, U> implements DataSupplier {


    @Override
    public U forSchema(Schema schema) {
        T schemaResolved = (T) schema;
        return generateDataFromSchema(schemaResolved);
    }


    public abstract U generateDataFromSchema(T schema);


}

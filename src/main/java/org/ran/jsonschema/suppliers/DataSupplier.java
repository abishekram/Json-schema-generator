package org.ran.jsonschema.suppliers;

import org.everit.json.schema.Schema;

public interface DataSupplier<T extends Schema, U> {

    U forSchema(T schema);

}

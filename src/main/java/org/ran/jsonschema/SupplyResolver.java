package org.ran.jsonschema;

import org.everit.json.schema.*;
import org.ran.jsonschema.suppliers.*;

import java.util.HashMap;
import java.util.Map;

import static org.ran.jsonschema.suppliers.SimpleRandomProvider.*;

public class SupplyResolver {

    private SupplyResolver() {
    }

    private final static Map<String, DataSupplier> availableSuppliers = new HashMap<String, DataSupplier>() {
        @Override
        public DataSupplier get(Object key) {
            return super.get(key) == null ? new DummyDataSupplier() : super.get(key);
        }
    };

    static {
        availableSuppliers.put(ObjectSchema.class.getName(), new ObjectDataSupplier());
        availableSuppliers.put(StringSchema.class.getName(), new StringDataGenerator());
        availableSuppliers.put(ArraySchema.class.getName(), new ArrayDataSupplier());
        availableSuppliers.put(NumberSchema.class.getName(), new NumberDataSupplier());
        availableSuppliers.put(BooleanSchema.class.getName(), booleanDataProvider);
        availableSuppliers.put(EnumSchema.class.getName(), enumDataProvider);
        availableSuppliers.put(ReferenceSchema.class.getName(), referenceDataProvider);
        availableSuppliers.put(NullSchema.class.getName(),nullDataProvider);
    }

    public static DataSupplier<Schema, String> resolve(Schema schema) {
        return availableSuppliers.get(schema.getClass().getName());
    }
}

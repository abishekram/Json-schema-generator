package org.ran.jsonschema.suppliers;

import org.everit.json.schema.BooleanSchema;
import org.everit.json.schema.EnumSchema;
import org.everit.json.schema.NullSchema;
import org.everit.json.schema.ReferenceSchema;
import org.ran.jsonschema.SupplyResolver;

import java.util.Random;

import static org.ran.jsonschema.RandomUtils.getRandomInteger;

public class SimpleRandomProvider {

    public static final AbstractDataSupplier<EnumSchema, String> enumDataProvider = new AbstractDataSupplier<EnumSchema, String>() {
        @Override
        public String generateDataFromSchema(EnumSchema schema) {
            int position = getRandomInteger(schema.getPossibleValues().size() - 1, 0, 1);
            return String.valueOf(schema.getPossibleValuesAsList().get(position));
        }
    };

    public static final AbstractDataSupplier<BooleanSchema, Boolean> booleanDataProvider = new AbstractDataSupplier<BooleanSchema, Boolean>() {
        @Override
        public Boolean generateDataFromSchema(BooleanSchema schema) {
            return new Random().nextBoolean();
        }
    };

    public static final AbstractDataSupplier<ReferenceSchema, Object> referenceDataProvider = new AbstractDataSupplier<ReferenceSchema, Object>() {
        @Override
        public Object generateDataFromSchema(ReferenceSchema schema) {
            return SupplyResolver.resolve(schema.getReferredSchema()).forSchema(schema.getReferredSchema());
        }

    };

    public static final AbstractDataSupplier<NullSchema, String> nullDataProvider = new AbstractDataSupplier<NullSchema, String>() {
        @Override
        public String generateDataFromSchema(NullSchema schema) {
            return null;
        }
    };
}

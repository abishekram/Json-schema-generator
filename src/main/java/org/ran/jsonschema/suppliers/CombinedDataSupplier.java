package org.ran.jsonschema.suppliers;

import org.everit.json.schema.CombinedSchema;
import org.everit.json.schema.NumberSchema;
import org.everit.json.schema.ObjectSchema;
import org.everit.json.schema.Schema;
import org.everit.json.schema.StringSchema;
import org.ran.jsonschema.JsonGenerationException;
import org.ran.jsonschema.SupplyResolver;

import static org.ran.jsonschema.RandomUtils.getRandomInteger;

public class CombinedDataSupplier extends AbstractDataSupplier<CombinedSchema, Object> {

    @Override
    public Object generateDataFromSchema(CombinedSchema schema) {
        String ofType = schema.getCriterion().toString();

        if ("allOf".equalsIgnoreCase(ofType)) {
            Schema result = schema.getSubschemas().stream().reduce(this::mergeSchemas).get();
            return SupplyResolver.resolve(result).forSchema(result);
        } else {
            // for oneOf and anyOf return one random element
            Schema subSchema = schema.getSubschemas().stream()
                    .skip(getRandomInteger(schema.getSubschemas().size()-1, 0, 1))
                    .findFirst()
                    .get();
            return SupplyResolver.resolve(subSchema).forSchema(subSchema);
        }
    }

    private Schema mergeSchemas(Schema left, Schema right) {
        if (!left.getClass().getName().equals(right.getClass().getName())) {
            throw new JsonGenerationException("allOf: sub schemas have different types");
        }

        switch (left.getClass().getName()) {
            case "org.everit.json.schema.StringSchema": return mergeStringSchemas((StringSchema) left, (StringSchema)right);
            case "org.everit.json.schema.NumberSchema": return mergeNumberSchemas((NumberSchema)left, (NumberSchema)right);
            case "org.everit.json.schema.ObjectSchema": return mergeObjectSchemas((ObjectSchema)left, (ObjectSchema)right);
            default: throw new JsonGenerationException("allOf: unknown type is unsupported = " + left.getClass().getName());
        }
    }

    private StringSchema mergeStringSchemas(final StringSchema left, final StringSchema right) {

        StringSchema.Builder builder = StringSchema.builder();

        // Merge maxLength
        if (left.getMaxLength() != null || right.getMaxLength() != null) {
            Integer maxLength = null;
            if (left.getMaxLength() == null) {
                maxLength = right.getMaxLength();
            } else if (right.getMaxLength() == null) {
                maxLength = left.getMaxLength();
            } else {
                maxLength = Math.min(left.getMaxLength(), right.getMaxLength());
            }
            builder = builder.maxLength(maxLength);
        }

        // Merge minLength
        if (left.getMinLength() != null || right.getMinLength() != null) {
            Integer minLength = null;
            if (left.getMinLength() == null) {
                minLength = right.getMinLength();
            } else if (right.getMinLength() == null) {
                minLength = left.getMinLength();
            } else {
                minLength = Math.max(left.getMinLength(), right.getMinLength());
            }
            builder = builder.minLength(minLength);
        }

        /* for future
        // Merge pattern
        if (left.getPattern() != null || right.getPattern() != null) {
            String patternString = null;
            if (left.getPattern() == null) {
                patternString = right.getPattern().pattern();
            } else if (right.getPattern() == null) {
                patternString = right.getPattern().pattern();
            } else {
                patternString = "(?=" + left.getPattern().pattern() + ")(?=" + right.getPattern().pattern() + ")";
            }
            builder = builder.pattern(patternString);
        }
        */

        // Merge require
        builder = builder.requiresString(left.requireString() || right.requireString());

        return builder.build();
    }

    private NumberSchema mergeNumberSchemas(final NumberSchema left, final NumberSchema right) {

        NumberSchema.Builder builder = NumberSchema.builder();

        // Merge requiresInteger and requiresNumber
        if (left.requiresInteger() && right.isRequiresNumber() || left.isRequiresNumber() && right.requiresInteger()) {
            throw new JsonGenerationException("Numbers merge is impossible: requiresInteger and requiresNumber both presented");
        }
        builder = builder.requiresInteger(left.requiresInteger()).requiresNumber(left.isRequiresNumber());

        // Merge multipleOf
        Number multipleOf = 1;
        if (left.getMultipleOf() != null) {
            multipleOf = left.getMultipleOf().doubleValue() * multipleOf.doubleValue();
        }
        if (right.getMultipleOf() != null) {
            multipleOf = right.getMultipleOf().doubleValue() * multipleOf.doubleValue();
        }
        builder = builder.multipleOf(multipleOf);

        // Merge maximum
        if (left.getMaximum() != null || right.getMaximum() != null) {
            Number maxLength;
            if (left.getMaximum() == null) {
                maxLength = right.isExclusiveMaximum() ? right.getMaximum().doubleValue() - 1 : right.getMaximum();
            } else if (right.getMaximum() == null) {
                maxLength = left.isExclusiveMaximum() ? left.getMaximum().doubleValue() - 1 : left.getMaximum();
            } else {
                maxLength = Math.min(left.getMaximum().doubleValue(), right.getMaximum().doubleValue());
            }
            builder = builder.maximum(maxLength);
        }

        // Merge minimum
        if (left.getMinimum() != null || right.getMinimum() != null) {
            Number minLength;
            if (left.getMinimum() == null) {
                minLength = right.isExclusiveMinimum() ? right.getMinimum().doubleValue() + 1 : right.getMinimum();
            } else if (right.getMinimum() == null) {
                minLength = left.isExclusiveMinimum() ? left.getMinimum().doubleValue() + 1 : left.getMinimum();
            } else {
                minLength = Math.max(left.getMinimum().doubleValue(), right.getMinimum().doubleValue());
            }
            builder = builder.minimum(minLength);
        }

        return builder.build();
    }

    private ObjectSchema mergeObjectSchemas(final ObjectSchema left, final ObjectSchema right) {
        if (!left.permitsAdditionalProperties() || !right.permitsAdditionalProperties()) {
            throw new JsonGenerationException("object: additionalProperties aren't permitted");
        }

        ObjectSchema.Builder builder = ObjectSchema.builder();
        for (String property : left.getRequiredProperties()) {
            builder = builder.addPropertySchema(property, left.getPropertySchemas().get(property)).addRequiredProperty(property);
        };

        for (String property : right.getRequiredProperties()) {
            builder = builder.addPropertySchema(property, right.getPropertySchemas().get(property)).addRequiredProperty(property);
        };
        return builder.build();
    }
}

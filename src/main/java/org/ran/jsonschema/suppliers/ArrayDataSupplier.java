package org.ran.jsonschema.suppliers;

import org.everit.json.schema.ArraySchema;

import org.ran.jsonschema.SupplyResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArrayDataSupplier extends AbstractDataSupplier<ArraySchema, List> {

    @Override
    public List generateDataFromSchema(ArraySchema schema) {

        List<Object> arrayItems = new ArrayList<>();
        if (schema.getItemSchemas() == null) {
            appendItem(arrayItems, schema);
        } else {
            appendItems(arrayItems,schema);
        }
        return arrayItems;

    }

    private void appendItem(List<Object> arrayitems, ArraySchema schema) {
        int maxItems = Optional.ofNullable(schema.getMaxItems()).orElse(5);
        int minItems = Optional.ofNullable(schema.getMinItems()).orElse(1);
        RandomNumberSupplier randomNumberSupplier = new RandomNumberSupplier();
        int randomTotalItems = randomNumberSupplier.getRandomInteger(maxItems, minItems);
        while (arrayitems.size() <= randomTotalItems) {
            appendSingleItem(arrayitems, schema);
        }
    }

    private void appendSingleItem(List<Object> arrayItems, ArraySchema schema) {
        arrayItems.add(SupplyResolver.resolve(schema.getAllItemSchema()).forSchema(schema.getAllItemSchema()));
    }

    private void appendItems(List<Object> jsonArray, ArraySchema schema) {
        schema.getItemSchemas().forEach(itemSchema ->
                jsonArray.add(SupplyResolver.resolve(itemSchema).forSchema(itemSchema))
        );

    }
}

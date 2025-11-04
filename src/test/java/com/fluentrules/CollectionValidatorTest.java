package com.fluentrules;

import com.fluentrules.core.AbstractValidator;
import com.fluentrules.core.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectionValidatorTest {
    static class Item {
        private String name;

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }
    }

    static class ItemValidator extends AbstractValidator<Item> {
        ItemValidator() {
            RuleFor(Item::getName).NotEmpty().WithMessage("Name required");
        }
    }

    static class Container {
        private final List<Item> items = new ArrayList<>();

        List<Item> getItems() {
            return items;
        }
    }

    static class ContainerValidator extends AbstractValidator<Container> {
        ContainerValidator() {
            RuleForEach(Container::getItems).SetValidator(new ItemValidator());
        }
    }

    @Test
    void shouldAnnotateCollectionErrorsWithIndex() {
        Container container = new Container();
        Item invalid = new Item();
        invalid.setName("");
        container.getItems().add(invalid);
        ValidationResult result = new ContainerValidator().validate(container);
        assertFalse(result.isValid());
        assertEquals("items[0].name", result.getErrors().get(0).getField());
    }

    @Test
    void shouldPassWhenAllItemsValid() {
        Container container = new Container();
        Item item = new Item();
        item.setName("Item");
        container.getItems().add(item);
        ValidationResult result = new ContainerValidator().validate(container);
        assertTrue(result.isValid());
    }
}

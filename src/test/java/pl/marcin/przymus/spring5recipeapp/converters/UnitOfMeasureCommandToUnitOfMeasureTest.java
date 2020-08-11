package pl.marcin.przymus.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.marcin.przymus.spring5recipeapp.commands.UnitOfMeasureCommand;
import pl.marcin.przymus.spring5recipeapp.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void testEmptyObject() {
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        UnitOfMeasure unitOfMeasure = converter.convert(command);
        assertNotNull(unitOfMeasure);
    }

    @Test
    void testConvert() {
        String description = "description";
        UnitOfMeasureCommand given = new UnitOfMeasureCommand();
        given.setId(1L);
        given.setDescription(description);

        UnitOfMeasure result = converter.convert(given);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(description, result.getDescription());
    }
}
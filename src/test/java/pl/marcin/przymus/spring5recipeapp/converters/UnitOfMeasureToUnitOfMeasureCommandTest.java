package pl.marcin.przymus.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.marcin.przymus.spring5recipeapp.commands.UnitOfMeasureCommand;
import pl.marcin.przymus.spring5recipeapp.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void testEmptyObject() {
        UnitOfMeasure uom = new UnitOfMeasure();
        UnitOfMeasureCommand unitOfMeasure = converter.convert(uom);
        assertNotNull(unitOfMeasure);
    }

    @Test
    void testConvert() {
        String description = "description";
        UnitOfMeasure given = new UnitOfMeasure();
        given.setId(1L);
        given.setDescription(description);

        UnitOfMeasureCommand result = converter.convert(given);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(description, result.getDescription());
    }
}
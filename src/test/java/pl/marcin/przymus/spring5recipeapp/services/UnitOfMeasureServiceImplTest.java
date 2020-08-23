package pl.marcin.przymus.spring5recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.marcin.przymus.spring5recipeapp.commands.UnitOfMeasureCommand;
import pl.marcin.przymus.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import pl.marcin.przymus.spring5recipeapp.domain.UnitOfMeasure;
import pl.marcin.przymus.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureService tested;

    private final UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();

    @Mock
    UnitOfMeasureRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new UnitOfMeasureServiceImpl(repository, converter);
    }

    @Test
    void listAllUnitsOfMeasure() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);

        when(repository.findAll()).thenReturn(Set.of(uom1, uom2));

        Set<UnitOfMeasureCommand> result = tested.listAllUnitsOfMeasure();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository).findAll();
    }
}
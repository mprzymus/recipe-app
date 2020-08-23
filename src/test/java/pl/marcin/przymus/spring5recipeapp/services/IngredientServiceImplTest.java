package pl.marcin.przymus.spring5recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.przymus.spring5recipeapp.commands.IngredientCommand;
import pl.marcin.przymus.spring5recipeapp.converters.IngredientCommandToIngredient;
import pl.marcin.przymus.spring5recipeapp.converters.IngredientToIngredientCommand;
import pl.marcin.przymus.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import pl.marcin.przymus.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Ingredient;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.repositories.RecipeRepository;
import pl.marcin.przymus.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private IngredientServiceImpl tested;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand =
            new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand,
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndIngredientIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        IngredientCommand result = tested.findByRecipeIdAndIngredientId(1L, 2L);

        assertEquals(2L, result.getId());
        assertEquals(1L, result.getRecipeId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void saveIngredientCommandTest() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setRecipeId(3L);

        Recipe recipe = new Recipe();
        recipe.setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any())).thenReturn(recipe);
        IngredientCommand result = tested.saveIngredientCommand(ingredientCommand);

        assertEquals(ingredientCommand.getId(), result.getId());
        assertEquals(ingredientCommand.getRecipeId(), result.getRecipeId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void shouldDeleteById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        tested.deleteById(1L, 2L);
        ArgumentCaptor<Recipe> savedCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeRepository).save(savedCaptor.capture());

        Recipe result = savedCaptor.getValue();
        assertEquals(1, result.getIngredients().size());
        assertTrue(result.getIngredients().contains(ingredient1));
        assertFalse(result.getIngredients().contains(ingredient2));
    }
}
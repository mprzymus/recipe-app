package pl.marcin.przymus.spring5recipeapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.przymus.spring5recipeapp.converters.RecipeCommandToRecipe;
import pl.marcin.przymus.spring5recipeapp.converters.RecipeToRecipeCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;


    @Test
    void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        var recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(eq(1L))).thenReturn(recipeOptional);
        Recipe returned = recipeService.findById(1L);

        assertNotNull(returned);
        assertEquals(1L, returned.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipesTest() {
        Recipe recipe = new Recipe();
        var recipesData = new HashSet<Recipe>();
        recipesData.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipesData);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        Long idToDelete = 2L;
        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(idToDelete);
    }
}
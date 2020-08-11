package pl.marcin.przymus.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipesControllerTest {

    RecipesController tested;

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new RecipesController(recipeService);
    }

    @Test
    void getRecipeShouldCallTest() {
        Long id = 1L;
        var recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.findById(eq(id))).thenReturn(recipe);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        tested.showById(id.toString(), model);
        verify(recipeService, times(1)).findById(eq(id));
        verify(model, times(1)).addAttribute(eq("recipe"), argumentCaptor.capture());
        assertEquals(recipe.getId(), argumentCaptor.getValue().getId());
    }

    @Test
    void testGetRecipe() throws Exception {
        Long id = 1L;
        var recipe = new Recipe();
        recipe.setId(id);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tested).build();

        when(recipeService.findById(eq(id))).thenReturn(recipe);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }
}
package pl.marcin.przymus.spring5recipeapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.exceptions.NotFoundException;
import pl.marcin.przymus.spring5recipeapp.services.RecipeService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ControllerExceptionHandlerTest {

    RecipesController recipesController;

    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipesController = new RecipesController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipesController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

    }

    @Test
    public void shouldThrowExceptionWhenNotFoundRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    void stringInsteadOfNumberAsIdTest() throws Exception {
        mockMvc.perform(get("/recipe/dwa/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}
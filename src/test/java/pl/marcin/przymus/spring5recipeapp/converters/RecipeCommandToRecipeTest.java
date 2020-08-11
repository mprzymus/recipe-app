package pl.marcin.przymus.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.marcin.przymus.spring5recipeapp.commands.CategoryCommand;
import pl.marcin.przymus.spring5recipeapp.commands.IngredientCommand;
import pl.marcin.przymus.spring5recipeapp.commands.NotesCommand;
import pl.marcin.przymus.spring5recipeapp.commands.RecipeCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Difficulty;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeCommandToRecipeTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID_2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;

    private RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(), new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), new CategoryCommandToCategory());
    }

    @Test
    void shouldNotReturnNullWhenGotEmptyCommand() {
        var given = new RecipeCommand();
        assertNotNull(converter.convert(given));
    }

    @Test
    void shouldReturnRecipeWithEqualFieldsToRecipeCommand() {
        var given = new RecipeCommand();
        given.setId(RECIPE_ID);
        given.setDescription(DESCRIPTION);
        given.setPrepTime(PREP_TIME);
        given.setCookTime(COOK_TIME);
        given.setServings(SERVINGS);
        given.setSource(SOURCE);
        given.setUrl(URL);
        given.setDescription(DESCRIPTION);
        given.setDirections(DIRECTIONS);
        given.setIngredients(new HashSet<>());
        IngredientCommand ingredient1 = new IngredientCommand();
        ingredient1.setId(INGRED_ID_1);
        given.getIngredients().add(ingredient1);
        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient1.setId(INGRED_ID_2);
        given.getIngredients().add(ingredient2);
        given.setDifficulty(DIFFICULTY);
        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        given.setNotes(notes);
        given.setCategories(new HashSet<>());
        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID_1);
        given.getCategories().add(categoryCommand1);
        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID_2);
        given.getCategories().add(categoryCommand2);

        var result = converter.convert(given);

        assertNotNull(result);
        assertEquals(RECIPE_ID, result.getId());
        assertEquals(COOK_TIME, result.getCookTime());
        assertEquals(PREP_TIME, result.getPrepTime());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(DIFFICULTY, result.getDifficulty());
        assertEquals(DIRECTIONS, result.getDirections());
        assertEquals(SERVINGS, result.getServings());
        assertEquals(SOURCE, result.getSource());
        assertEquals(URL, result.getUrl());
        assertEquals(NOTES_ID, result.getNotes().getId());
        assertEquals(2, result.getCategories().size());
        assertEquals(2, result.getIngredients().size());
    }
}
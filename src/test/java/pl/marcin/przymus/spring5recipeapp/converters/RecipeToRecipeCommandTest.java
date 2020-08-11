package pl.marcin.przymus.spring5recipeapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.marcin.przymus.spring5recipeapp.domain.*;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeToRecipeCommandTest {

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

    private RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(), new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()), new CategoryToCategoryCommand());
    }

    @Test
    void shouldNotReturnNullWhenGotEmptyCommand() {
        var given = new Recipe();
        assertNotNull(converter.convert(given));
    }

    @Test
    void shouldReturnRecipeWithEqualFieldsToRecipeCommand() {
        var given = new Recipe();
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
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGRED_ID_1);
        given.getIngredients().add(ingredient1);
        Ingredient ingredient2 = new Ingredient();
        ingredient1.setId(INGRED_ID_2);
        given.getIngredients().add(ingredient2);
        given.setDifficulty(DIFFICULTY);
        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        given.setNotes(notes);
        given.setCategories(new HashSet<>());
        Category category1 = new Category();
        category1.setId(CAT_ID_1);
        given.getCategories().add(category1);
        Category category2 = new Category();
        category2.setId(CAT_ID_2);
        given.getCategories().add(category2);

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
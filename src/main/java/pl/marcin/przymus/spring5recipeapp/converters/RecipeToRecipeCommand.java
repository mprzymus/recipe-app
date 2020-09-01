package pl.marcin.przymus.spring5recipeapp.converters;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.marcin.przymus.spring5recipeapp.commands.RecipeCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final CategoryToCategoryCommand categoryConverter;
    @Override
    @Synchronized
    @Nullable
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) return null;
        var toReturn = new RecipeCommand();
        toReturn.setId(recipe.getId());
        toReturn.setDescription(recipe.getDescription());
        toReturn.setPrepTime(recipe.getPrepTime());
        toReturn.setCookTime(recipe.getCookTime());
        toReturn.setServings(recipe.getServings());
        toReturn.setSource(recipe.getSource());
        toReturn.setUrl(recipe.getUrl());
        toReturn.setDirections(recipe.getDirections());
        toReturn.setNotes(notesConverter.convert(recipe.getNotes()));
        if (recipe.getIngredients() != null) {
            var ingredients = recipe.getIngredients().stream()
                    .map(ingredientConverter::convert)
                    .collect(Collectors.toSet());
            toReturn.setIngredients(ingredients);
        }
        if (recipe.getCategories() != null) {
            var categories = recipe.getCategories().stream()
                    .map(categoryConverter::convert)
                    .collect(Collectors.toSet());
            toReturn.setCategories(categories);
        }
        toReturn.setDifficulty(recipe.getDifficulty());
        toReturn.setImage(recipe.getImage());
        return toReturn;
    }
}

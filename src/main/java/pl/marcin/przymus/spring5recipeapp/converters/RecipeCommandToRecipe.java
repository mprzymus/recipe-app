package pl.marcin.przymus.spring5recipeapp.converters;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.marcin.przymus.spring5recipeapp.commands.RecipeCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final CategoryCommandToCategory categoryConverter;
    @Override
    @Synchronized
    @Nullable
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null) return null;
        var toReturn = new Recipe();
        toReturn.setId(recipeCommand.getId());
        toReturn.setDescription(recipeCommand.getDescription());
        toReturn.setPrepTime(recipeCommand.getPrepTime());
        toReturn.setCookTime(recipeCommand.getCookTime());
        toReturn.setServings(recipeCommand.getServings());
        toReturn.setSource(recipeCommand.getSource());
        toReturn.setUrl(recipeCommand.getUrl());
        toReturn.setDirections(recipeCommand.getDirections());
        toReturn.setNotes(notesConverter.convert(recipeCommand.getNotes()));
        if (recipeCommand.getIngredients() != null) {
            var ingredients = recipeCommand.getIngredients().stream()
                    .map(ingredientConverter::convert)
                    .collect(Collectors.toSet());
            toReturn.setIngredients(ingredients);
        }
        if (recipeCommand.getCategories() != null) {
            var categories = recipeCommand.getCategories().stream()
                    .map(categoryConverter::convert)
                    .collect(Collectors.toSet());
            toReturn.setCategories(categories);
        }
        toReturn.setDifficulty(recipeCommand.getDifficulty());
        toReturn.setImage(recipeCommand.getImage());
        return toReturn;
    }
}

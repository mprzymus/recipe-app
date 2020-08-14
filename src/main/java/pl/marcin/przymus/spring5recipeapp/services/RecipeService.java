package pl.marcin.przymus.spring5recipeapp.services;

import pl.marcin.przymus.spring5recipeapp.commands.RecipeCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand toSave);
}

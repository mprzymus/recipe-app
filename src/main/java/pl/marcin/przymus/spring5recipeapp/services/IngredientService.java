package pl.marcin.przymus.spring5recipeapp.services;

import pl.marcin.przymus.spring5recipeapp.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}

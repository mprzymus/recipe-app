package pl.marcin.przymus.spring5recipeapp.services;

import pl.marcin.przymus.spring5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    public Set<Recipe> getRecipes();

    Recipe findById(Long id);
}

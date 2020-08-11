package pl.marcin.przymus.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}

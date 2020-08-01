package pl.marcin.przymus.spring5recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> set = new HashSet<>();
        recipeRepository.findAll().forEach(set::add);
        return set;
    }
}

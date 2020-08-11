package pl.marcin.przymus.spring5recipeapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.marcin.przymus.spring5recipeapp.commands.RecipeCommand;
import pl.marcin.przymus.spring5recipeapp.converters.RecipeCommandToRecipe;
import pl.marcin.przymus.spring5recipeapp.converters.RecipeToRecipeCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe converter;
    private final RecipeToRecipeCommand reconverter;
    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> set = new HashSet<>();
        recipeRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand toSave) {
        Recipe detachedRecipe = converter.convert(toSave);
        Recipe saved = recipeRepository.save(detachedRecipe);
        log.debug("Saved recipe id: {}", saved.getId());
        return reconverter.convert(saved);
    }
}

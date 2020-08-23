package pl.marcin.przymus.spring5recipeapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.przymus.spring5recipeapp.commands.IngredientCommand;
import pl.marcin.przymus.spring5recipeapp.converters.IngredientCommandToIngredient;
import pl.marcin.przymus.spring5recipeapp.converters.IngredientToIngredientCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Ingredient;
import pl.marcin.przymus.spring5recipeapp.domain.Recipe;
import pl.marcin.przymus.spring5recipeapp.repositories.RecipeRepository;
import pl.marcin.przymus.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        var recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            log.error("recipe not found. Id: {}", recipeId);
            return null;
        }

        Recipe recipe = recipeOptional.get();
        var ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();
        if (ingredientOptional.isEmpty()) {
            log.error("No ingredient with such id in recipe. Recipe id: {}, ingredient id: {}", recipeId, ingredientId);
            return null;
        }
        return ingredientToIngredientCommand.convert(ingredientOptional.get());
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        var recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (recipeOptional.isEmpty()) {
            log.error("Recipe not found for id: {}", command.getRecipeId());
            return new IngredientCommand();
        }
        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findAny();
        if (optionalIngredient.isPresent()) {
            Ingredient ingredientFound = optionalIngredient.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                    .findById(command.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("UOM not found")));
        } else {
            Ingredient ingredient = ingredientCommandToIngredient.convert(command);
            recipe.addIngredient(ingredient);
            ingredient.setRecipe(recipe);

        }
        Recipe savedRecipe = recipeRepository.save(recipe);
        var savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
                .findAny();
        if (savedIngredientOptional.isEmpty()) {
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredient -> recipeIngredient.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredient -> recipeIngredient.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredient -> recipeIngredient.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                    .findAny();
        }
        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            log.error("Try to delete ingredient for not existing recipe. RecipeId: {}, IngredientId: {}", recipeId, ingredientId);
        }
        else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findAny();
            if (ingredientOptional.isEmpty()) {
                log.error("No ingredient with id: {} for recipe with id: {}", ingredientId, recipeId);
            }
            else {
                recipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(ingredientId));
                recipeRepository.save(recipe);
            }
        }
    }
}

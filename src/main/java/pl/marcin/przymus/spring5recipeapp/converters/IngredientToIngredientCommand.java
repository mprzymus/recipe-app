package pl.marcin.przymus.spring5recipeapp.converters;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.marcin.przymus.spring5recipeapp.commands.IngredientCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Ingredient;

@Component
@RequiredArgsConstructor
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    @Override
    @Synchronized
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) return null;
        var toReturn = new IngredientCommand();
        toReturn.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            toReturn.setRecipeId(ingredient.getRecipe().getId());
        }
        toReturn.setDescription(ingredient.getDescription());
        toReturn.setAmount(ingredient.getAmount());
        toReturn.setUnitOfMeasure(uomConverter.convert(ingredient.getUnitOfMeasure()));
        return toReturn;
    }
}

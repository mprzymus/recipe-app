package pl.marcin.przymus.spring5recipeapp.converters;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.marcin.przymus.spring5recipeapp.commands.IngredientCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Ingredient;

@Component
@RequiredArgsConstructor
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @Synchronized
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) return null;
        var toReturn = new Ingredient();
        toReturn.setId(ingredientCommand.getId());
        toReturn.setAmount(ingredientCommand.getAmount());
        toReturn.setDescription(ingredientCommand.getDescription());
        toReturn.setUnitOfMeasure(uomConverter.convert(ingredientCommand.getUnitOfMeasure()));
        return toReturn;
    }
}

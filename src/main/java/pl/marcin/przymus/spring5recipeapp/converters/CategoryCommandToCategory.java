package pl.marcin.przymus.spring5recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.marcin.przymus.spring5recipeapp.commands.CategoryCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Category;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if (categoryCommand == null) return null;
        var toReturn = new Category();
        toReturn.setId(categoryCommand.getId());
        toReturn.setDescription(categoryCommand.getDescription());
        return toReturn;
    }
}

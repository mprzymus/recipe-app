package pl.marcin.przymus.spring5recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.marcin.przymus.spring5recipeapp.commands.CategoryCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Category;


@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category category) {
        if(category == null) return null;
        var toReturn = new CategoryCommand();
        toReturn.setId(category.getId());
        toReturn.setDescription(category.getDescription());
        return toReturn;
    }
}

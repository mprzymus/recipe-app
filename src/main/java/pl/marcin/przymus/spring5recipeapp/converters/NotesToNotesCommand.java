package pl.marcin.przymus.spring5recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.marcin.przymus.spring5recipeapp.commands.NotesCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Override
    @Synchronized
    @Nullable
    public NotesCommand convert(Notes notes) {
        if (notes == null) return null;
        var toReturn = new NotesCommand();
        toReturn.setId(notes.getId());
        toReturn.setRecipeNotes(notes.getRecipeNotes());
        return toReturn;
    }
}

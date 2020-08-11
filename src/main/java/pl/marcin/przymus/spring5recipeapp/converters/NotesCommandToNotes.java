package pl.marcin.przymus.spring5recipeapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.marcin.przymus.spring5recipeapp.commands.NotesCommand;
import pl.marcin.przymus.spring5recipeapp.domain.Notes;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Override
    @Synchronized
    @Nullable
    public Notes convert(NotesCommand notesCommand) {
        if (notesCommand == null)return null;
        var toReturn = new Notes();
        toReturn.setId(notesCommand.getId());
        toReturn.setRecipeNotes(notesCommand.getRecipeNotes());
        return toReturn;
    }
}

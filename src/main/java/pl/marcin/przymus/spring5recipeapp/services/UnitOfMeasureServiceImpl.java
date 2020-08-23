package pl.marcin.przymus.spring5recipeapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.przymus.spring5recipeapp.commands.UnitOfMeasureCommand;
import pl.marcin.przymus.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import pl.marcin.przymus.spring5recipeapp.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    @Override
    public Set<UnitOfMeasureCommand> listAllUnitsOfMeasure() {
        Set<UnitOfMeasureCommand> toReturn = new HashSet<>();
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(converter::convert)
                .collect(Collectors.toSet());
    }
}

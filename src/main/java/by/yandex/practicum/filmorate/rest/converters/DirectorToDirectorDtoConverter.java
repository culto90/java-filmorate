package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.models.Director;
import by.yandex.practicum.filmorate.rest.dto.DirectorDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DirectorToDirectorDtoConverter implements Converter<Director, DirectorDto> {
    @Override
    public DirectorDto convert(Director director) {
        return new DirectorDto(director.getId(), director.getName());
    }
}

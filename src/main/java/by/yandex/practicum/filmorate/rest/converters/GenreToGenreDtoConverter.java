package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.rest.dto.GenreDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenreToGenreDtoConverter implements Converter<Genre, GenreDto> {
    @Override
    public GenreDto convert(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName(), genre.getDescription());
    }
}

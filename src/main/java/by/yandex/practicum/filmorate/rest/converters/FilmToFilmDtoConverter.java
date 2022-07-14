package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.exceptions.DtoConverterException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.rest.dto.FilmDto;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FilmToFilmDtoConverter implements Converter<Film, FilmDto> {

    @SneakyThrows
    @Override
    public FilmDto convert(Film film) {
        if (film == null) {
            throw new DtoConverterException("Cannot convert input object.");
        }
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDescription(film.getDescription());
        filmDto.setReleaseDate(film.getReleaseDate());
        filmDto.setDuration(film.getDuration());
        filmDto.setRate(film.getRate());
        filmDto.setMpa(film.getRating());
        filmDto.setGenres(film.getGenres());
        filmDto.setLikes(film.getLikes());
        return filmDto;
    }
}

package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.exceptions.DtoConverterException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.rest.dto.FilmDto;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FilmDtoToFilmConverter implements Converter<FilmDto, Film> {
    @SneakyThrows
    @Override
    public Film convert(FilmDto filmDto) {
        if (filmDto == null) {
            throw new DtoConverterException("Cannot convert output object.");
        }
        Film film = new Film();
        film.setId(filmDto.getId());
        film.setName(filmDto.getName());
        film.setDescription(filmDto.getDescription());
        film.setReleaseDate(filmDto.getReleaseDate());
        film.setDuration(filmDto.getDuration());
        film.setRate(filmDto.getRate());
        film.setRating(filmDto.getMpa());
        film.setGenreList(filmDto.getGenres());
        film.setLikeList(filmDto.getLikes());
        film.setDirectorList(filmDto.getDirectors());
        return film;
    }
}

package by.yandex.practicum.filmorate.rest.controllers;

import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.rest.converters.FilmDtoToFilmConverter;
import by.yandex.practicum.filmorate.rest.converters.FilmToFilmDtoConverter;
import by.yandex.practicum.filmorate.rest.dto.FilmDto;
import by.yandex.practicum.filmorate.services.FilmService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
public class FilmController {
    private FilmService filmService;
    private FilmToFilmDtoConverter toFilmDtoConverter;
    private FilmDtoToFilmConverter toFilmConverter;

    public FilmController (FilmService filmService,
                           FilmToFilmDtoConverter toFilmDtoConverter,
                           FilmDtoToFilmConverter toFilmConverter) {
        this.filmService = filmService;
        this.toFilmDtoConverter = toFilmDtoConverter;
        this.toFilmConverter = toFilmConverter;
    }

    @GetMapping("/films")
    public List<FilmDto> getAll() {
        List<Film> films = filmService.getAllFilms();
        List<FilmDto> filmsDto = films.stream()
                .map(toFilmDtoConverter::convert)
                .collect(Collectors.toList());
        return filmsDto;
    }

    @GetMapping("/films/{id}")
    public FilmDto getOne(@PathVariable Long id) {
        FilmDto filmDto = toFilmDtoConverter.convert(filmService.getFilmById(id));
        return filmDto;
    }

    @PostMapping("/films")
    public FilmDto addFilm(@Valid @RequestBody FilmDto newFilmDto) throws FilmServiceException {
        Film film = toFilmConverter.convert(newFilmDto);
        FilmDto createdFilmDto = toFilmDtoConverter.convert(filmService.addFilm(film));
        return createdFilmDto;
    }

    @PutMapping("/films")
    public FilmDto updateFilm(@Valid @RequestBody FilmDto newFilmDto) throws FilmServiceException {
        Film film = toFilmConverter.convert(newFilmDto);
        FilmDto updatedFilmDto = toFilmDtoConverter.convert(filmService.updateFilm(film));
        return updatedFilmDto;
    }
}

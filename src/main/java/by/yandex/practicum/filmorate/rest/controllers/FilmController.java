package by.yandex.practicum.filmorate.rest.controllers;

import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.rest.converters.FilmDtoToFilmConverter;
import by.yandex.practicum.filmorate.rest.converters.FilmToFilmDtoConverter;
import by.yandex.practicum.filmorate.rest.converters.LikeToLikeDtoConverter;
import by.yandex.practicum.filmorate.rest.dto.FilmDto;
import by.yandex.practicum.filmorate.rest.dto.LikeDto;
import by.yandex.practicum.filmorate.services.FilmService;
import by.yandex.practicum.filmorate.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
public class FilmController {
    private final FilmService filmService;
    private final LikeService likeService;
    private final FilmToFilmDtoConverter toFilmDtoConverter;
    private final FilmDtoToFilmConverter toFilmConverter;
    private final LikeToLikeDtoConverter toLikeDtoConverter;

    @Autowired
    public FilmController(FilmService filmService,
                          LikeService likeService,
                          FilmToFilmDtoConverter toFilmDtoConverter,
                          FilmDtoToFilmConverter toFilmConverter,
                          LikeToLikeDtoConverter toLikeDtoConverter) {
        this.filmService = filmService;
        this.likeService = likeService;
        this.toFilmDtoConverter = toFilmDtoConverter;
        this.toFilmConverter = toFilmConverter;
        this.toLikeDtoConverter = toLikeDtoConverter;
    }

    @GetMapping("/films")
    public List<FilmDto> getAll() {
        return filmService.getAllFilms()
                .stream()
                .map(toFilmDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/films/{id}")
    public FilmDto getOne(@PathVariable Long id) {
        return toFilmDtoConverter.convert(filmService.getFilmById(id));
    }

    @GetMapping("/films/popular")
    public @ResponseBody List<FilmDto> getPopularFilms(@RequestParam(required = false, name = "count") Integer count,
                                                       @RequestParam(required = false, name = "genreId") Integer genreId,
                                                       @RequestParam(required = false, name = "year") Integer year) {
        return filmService.getPopularFilmsByGenreAndYear(count, genreId, year)
                .stream()
                .map(toFilmDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @PostMapping("/films")
    public FilmDto addFilm(@Valid @RequestBody FilmDto newFilmDto) throws FilmServiceException {
        Film film = toFilmConverter.convert(newFilmDto);
        return toFilmDtoConverter.convert(filmService.addFilm(film));
    }

    @PutMapping("/films")
    public FilmDto updateFilm(@Valid @RequestBody FilmDto newFilmDto) throws FilmServiceException {
        Film film = toFilmConverter.convert(newFilmDto);
        return toFilmDtoConverter.convert(filmService.updateFilm(film));
    }

    @PutMapping("/films/{id}/like/{userId}")
    public LikeDto addLikeToFilm(@PathVariable long id, @PathVariable long userId) {
        return toLikeDtoConverter.convert(likeService.addLike(id, userId));
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void removeLikeFromFilm(@PathVariable long id, @PathVariable long userId) {
        likeService.removeLike(id, userId);
    }

    @DeleteMapping("/films/{id}")
    public FilmDto removeFilmById(@PathVariable long id) {
        return toFilmDtoConverter.convert(filmService.removeFilmById(id));
    }
}
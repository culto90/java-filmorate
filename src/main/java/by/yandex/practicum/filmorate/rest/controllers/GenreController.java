package by.yandex.practicum.filmorate.rest.controllers;

import by.yandex.practicum.filmorate.rest.converters.GenreToGenreDtoConverter;
import by.yandex.practicum.filmorate.rest.dto.GenreDto;
import by.yandex.practicum.filmorate.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreController {
    private final GenreService genreService;
    private final GenreToGenreDtoConverter genreDtoConverter;

    @Autowired
    public GenreController(GenreService genreService, GenreToGenreDtoConverter genreDtoConverter) {
        this.genreService = genreService;
        this.genreDtoConverter = genreDtoConverter;
    }

    @GetMapping("/genres")
    public List<GenreDto> getAll() {
        return genreService.getAllGenres()
                .stream()
                .map(genreDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/genres/{id}")
    public GenreDto getOneById(@PathVariable Long id) {
        return genreDtoConverter.convert(genreService.getGenreById(id));
    }
}

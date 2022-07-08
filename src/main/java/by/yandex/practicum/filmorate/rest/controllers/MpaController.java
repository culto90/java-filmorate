package by.yandex.practicum.filmorate.rest.controllers;

import by.yandex.practicum.filmorate.rest.converters.MpaRatingToMpaRatingDtoConverter;
import by.yandex.practicum.filmorate.rest.dto.MpaRatingDto;
import by.yandex.practicum.filmorate.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MpaController {
    private final RatingService ratingService;
    private final MpaRatingToMpaRatingDtoConverter ratingDtoConverter;

    @Autowired
    public MpaController(RatingService ratingService, MpaRatingToMpaRatingDtoConverter ratingDtoConverter) {
        this.ratingService = ratingService;
        this.ratingDtoConverter = ratingDtoConverter;
    }

    @GetMapping("/mpa")
    public List<MpaRatingDto> getAll() {
        return ratingService.getAllRatings()
                .stream()
                .map(ratingDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/mpa/{id}")
    public MpaRatingDto getOneById(@PathVariable Long id) {
        return ratingDtoConverter.convert(ratingService.getRatingById(id));
    }
}

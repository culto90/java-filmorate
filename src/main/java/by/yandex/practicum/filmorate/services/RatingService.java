package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.models.dictionaries.MpaRating;

import java.util.List;

public interface RatingService {
    List<MpaRating> getAllRatings();
    MpaRating getRatingById(long id);
}

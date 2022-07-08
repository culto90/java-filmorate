package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.MpaRatingNotFoundException;
import by.yandex.practicum.filmorate.models.dictionaries.MpaRating;
import by.yandex.practicum.filmorate.storages.MpaRatingStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MpaRatingService implements RatingService {
    private final MpaRatingStorage ratingStorage;

    @Autowired
    public MpaRatingService(MpaRatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    @Override
    public List<MpaRating> getAllRatings() {
        return ratingStorage.getAll();
    }

    @Override
    public MpaRating getRatingById(long id) {
        MpaRating rating = ratingStorage.getById(id);
        if (rating == null) {
            throw new MpaRatingNotFoundException("Rating with id = '" + id + "' not found.");
        }
        return ratingStorage.getById(id);
    }
}
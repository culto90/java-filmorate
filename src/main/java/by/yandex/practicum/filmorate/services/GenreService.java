package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.models.Genre;
import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();
    Genre getGenreById(long id);
}

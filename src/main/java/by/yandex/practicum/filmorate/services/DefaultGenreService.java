package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.GenreNotFoundException;
import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.storages.GenreStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DefaultGenreService implements GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public DefaultGenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreStorage.getAll();
    }

    @Override
    public Genre getGenreById(long id) {
        Genre genre = genreStorage.getById(id);
        if (genre == null) {
            throw new GenreNotFoundException("Genre with id = '" + id + "' not found.");
        }
        return genre;
    }
}

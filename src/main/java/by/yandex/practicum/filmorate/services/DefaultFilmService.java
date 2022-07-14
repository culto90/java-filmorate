package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultFilmService implements FilmService {
    private final static Integer POPULAR_FILM_LIMIT = 10;
    private final FilmStorage filmStorage;

    @Autowired
    public DefaultFilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Override
    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        int limit = count;
        if (limit == 0) {
            limit = POPULAR_FILM_LIMIT;
        }
        return filmStorage.getAll()
                .stream()
                .sorted(Comparator.comparing(Film::getLikeCount).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Film getFilmById(long id) {
        Film film = filmStorage.getById(id);
        if (film == null) {
            throw new FilmNotFoundException("Film with id = '" + id + "' not found.");
        }
        return film;
    }

    @Override
    public Film addFilm(Film newFilm) throws FilmServiceException {
        if (newFilm == null) {
            throw new FilmServiceException("Cannot add empty film.");
        }

        Long id = newFilm.getId();
        if (filmStorage.getById(id) != null) {
            throw new FilmServiceException("Film with this id already exists.");
        }

        Film film = filmStorage.put(newFilm);
        log.info("Film added: {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        Long id = updatedFilm.getId();
        Film foundFilm = filmStorage.getById(id);

        if (foundFilm == null) {
            throw new FilmNotFoundException("Film with id = '" + id + "' not found.");
        }

        log.info("Updated film: old value: {}", foundFilm);
        foundFilm.setReleaseDate(updatedFilm.getReleaseDate());
        foundFilm.setName(updatedFilm.getName());
        foundFilm.setDescription(updatedFilm.getDescription());
        foundFilm.setDuration(updatedFilm.getDuration());
        foundFilm.setRate(updatedFilm.getRate());
        foundFilm.setRating(updatedFilm.getRating());
        foundFilm.setGenreList(updatedFilm.getGenres());
        foundFilm.setLikeList(updatedFilm.getLikes());
        log.info("Updated film: new value: {}", foundFilm);

        return filmStorage.put(foundFilm);
    }
}
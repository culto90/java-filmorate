package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import by.yandex.practicum.filmorate.storages.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultFilmService implements FilmService {
    private final static Integer POPULAR_FILM_LIMIT = 10;
    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    @Autowired
    public DefaultFilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    @Override
    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    @Override
    public List<Film> getPopularFilmsByGenreAndYear(Integer count, Integer genreId, Integer year) {
        int limit = Objects.requireNonNullElse(count, POPULAR_FILM_LIMIT);
        List<Film> films =  filmStorage.getAll();
        if (genreId != null) {
            films = films.stream().filter(film -> film.hasGenre(genreId)).collect(Collectors.toList());
        }
        if (year != null) {
            films = films.stream().filter(film -> film.getReleaseDate().getYear() == year).collect(Collectors.toList());
        }

        return films.stream()
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
        foundFilm.setDirectorList(updatedFilm.getDirectors());
        foundFilm.setLikeList(updatedFilm.getLikes());
        log.info("Updated film: new value: {}", foundFilm);

        return filmStorage.put(foundFilm);
    }

    @Override
    public Film removeFilmById(long id) {
        return filmStorage.remove(filmStorage.getById(id));
    }

    @Override
    public List<Film> getCommonFilms(Long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        if (user == null) {
            throw new UserNotFoundException("User with id = '" + userId + "' not found.");
        }

        if (friend == null) {
            throw new UserNotFoundException("Friend with id = '" + userId + "' not found.");
        }

        List<Film> userFilms = user.getLikes().stream().map(Like::getFilm).collect(Collectors.toList());
        List<Film> friendFilms = user.getLikes().stream().map(Like::getFilm).collect(Collectors.toList());

        return userFilms.stream()
                .filter(friendFilms::contains)
                .sorted(Comparator.comparing(Film::getLikeCount).reversed())
                .collect(Collectors.toList());
    }
}
package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class InMemoryFilmService implements FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public InMemoryFilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Override
    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    @Override
    public Film getFilmById(long id) {
        Film film = filmStorage.getById(id);
        if (film == null) {
            throw new FilmNotFoundException("Film with id = '\" + id + \"' not found.");
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

        log.info("Film added: {}", newFilm);
        return filmStorage.put(newFilm);
    }

    @Override
    public Film updateFilm(Film updatedFilm) throws FilmServiceException {
        Film foundFilm = filmStorage.getById(updatedFilm.getId());

        if (foundFilm == null) {
            throw new FilmServiceException("Film with this id is not found.");
        }

        log.info("Updated film: old value: {}", foundFilm);
        foundFilm.setReleaseDate(updatedFilm.getReleaseDate());
        foundFilm.setName(updatedFilm.getName());
        foundFilm.setDescription(updatedFilm.getDescription());
        foundFilm.setDuration(updatedFilm.getDuration());
        foundFilm.setRate(updatedFilm.getRate());
        log.info("Updated film: new value: {}", foundFilm);

        return filmStorage.put(foundFilm);
    }
}

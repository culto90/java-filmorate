package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.models.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class InMemoryFilmService implements FilmService {
    private static final AtomicLong atomicLong = new AtomicLong(1);
    private final Map<Long, Film> films;

    public InMemoryFilmService() {
        films = new HashMap<>();
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(long id) {
        return films.get(id);
    }

    @Override
    public Film addFilm(Film film) throws FilmServiceException {
        if (films.get(film.getId()) != null) {
            throw new FilmServiceException("Film with this id already exists.");
        }

        long id = atomicLong.getAndIncrement();
        Film newFilm = new Film(id, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRate());
        films.put(newFilm.getId(), newFilm);
        log.info("Added new film: {}", film.toString());
        return newFilm;
    }

    @Override
    public Film updateFilm(Film film) throws FilmServiceException {
        Film foundFilm = films.get(film.getId());

        if (foundFilm == null) {
            throw new FilmServiceException("Film with this id is not found.");
        }
        log.info("Updated film: old value: {}", foundFilm.toString());

        foundFilm.setReleaseDate(film.getReleaseDate());
        foundFilm.setName(film.getName());
        foundFilm.setDescription(film.getDescription());
        foundFilm.setDuration(film.getDuration());
        foundFilm.setRate(film.getRate());

        log.info("Updated film: new value: {}", foundFilm.toString());

        return foundFilm;
    }
}

package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.DirectorNotFoundException;
import by.yandex.practicum.filmorate.exceptions.DirectorServiceException;
import by.yandex.practicum.filmorate.exceptions.InvalidParameterException;
import by.yandex.practicum.filmorate.models.Director;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.storages.DirectorStorage;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class DefaultDirectorService implements DirectorService{
    private final DirectorStorage directorStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public DefaultDirectorService(DirectorStorage directorStorage, FilmStorage filmStorage) {
        this.directorStorage = directorStorage;
        this.filmStorage = filmStorage;
    }

    @Override
    public List<Film> getDirectorFilmsSorted(long directorId, String type) {
        if (!(type.equals("year") || type.equals("likes"))) {
            throw new InvalidParameterException("Неверный параметр запроса GET /films/director/{directorId}?sortBy=");
        }
        log.trace("GET-запрос на список фильмов режиссёра {}, сортировка {} передан в БД.", directorId, type);
        if (directorStorage.getById(directorId) == null) {
            throw new DirectorNotFoundException(String.format("Режиссёр ID %d не найден.", directorId));
        }
        List<Film> films = filmStorage.getFilmsByDirectorId(directorId);
        switch (type) {
            case "likes": {
                films.sort(Comparator.comparingInt(Film::getLikeCount));
                break;
            }
            case "year": {
                films.sort(Comparator.comparing(Film::getReleaseDate));
                break;
            }
        }
        return films;
    }

    @Override
    public List<Director> getAll() {
        return directorStorage.getAll();
    }

    @Override
    public Director getById(long directorId) {
        Director director = directorStorage.getById(directorId);
        if (director == null) {
            throw new DirectorNotFoundException(String.format("Режиссёр ID %d не найден.", directorId));
        }
        return director;
    }

    @Override
    public Director add(Director director) throws DirectorServiceException {
        if (director == null) {
            throw new DirectorServiceException("Cannot add empty director.");
        }
        return directorStorage.add(director);
    }

    @Override
    public Director update(Director director) {
        return directorStorage.put(director);
    }

    @Override
    public Director remove(long directorId) {
        return directorStorage.remove(getById(directorId));
    }
}

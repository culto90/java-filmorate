package by.yandex.practicum.filmorate.services;

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
        log.trace("GET-запрос на список фильмов режиссёра {}, сортировка {} передан в БД.", directorId, type);
        getById(directorId);        // проверяем, существует ли такой директор
        List<Film> films = filmStorage.getDirectorFilms(directorId);
        switch (type) {
            case "likes": {
                films.sort(new Comparator<Film>() {
                    @Override
                    public int compare(Film o1, Film o2) {
                        return o1.getLikeCount() - o2.getLikeCount();
                    }
                });
                break;
            }
            case "year": {
                films.sort(new Comparator<Film>() {
                    @Override
                    public int compare(Film o1, Film o2) {
                        return o1.getReleaseDate().compareTo(o2.getReleaseDate());
                    }
                });
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
        return directorStorage.getById(directorId);
    }

    @Override
    public Director add(Director director) {
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

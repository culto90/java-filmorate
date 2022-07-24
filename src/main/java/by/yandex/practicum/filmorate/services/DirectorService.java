package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.models.Director;
import by.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface DirectorService {

    List<Director> getAll();

    Director getById(long directorId);

    Director add(Director director);

    Director update(Director director);

    Director remove(long directorId);

    List<Film> getDirectorFilmsSorted(long directorId, String type);
}



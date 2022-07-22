package by.yandex.practicum.filmorate.storages;

import by.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmStorage extends Storage<Film, Long>{

    List<Film> getDirectorFilms(long directorId);

}

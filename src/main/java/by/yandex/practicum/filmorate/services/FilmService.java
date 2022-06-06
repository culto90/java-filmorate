package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilms();
    Film getFilmById(long id);
    Film addFilm(Film film) throws FilmServiceException;
    Film updateFilm(Film film) throws FilmServiceException;
}

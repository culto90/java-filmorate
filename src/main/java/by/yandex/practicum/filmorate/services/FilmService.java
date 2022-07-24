package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilms();
    List<Film> getPopularFilmsByGenreAndYear(Integer count, Integer genreId, Integer year);
    Film getFilmById(long id);
    Film addFilm(Film film) throws FilmServiceException;
    Film updateFilm(Film film) throws FilmServiceException;
    Film removeFilmById(long id);
    List<Film> getCommonFilms(Long userId, Long friendId);
    List<Film> searchFilm(String query, String by);
}

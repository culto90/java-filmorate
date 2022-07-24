package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.models.Film;

import java.util.List;

public interface RecommendationsService {

    List<Film> getRecommendationsFilms(Long idUser);
}

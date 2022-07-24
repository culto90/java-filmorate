package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import by.yandex.practicum.filmorate.storages.UserStorage;
import by.yandex.practicum.filmorate.storages.dao.DbFilmStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DefaultRecommendationsService implements RecommendationsService{
    private final DbFilmStorage dbFilmStorage;

@Autowired
    public DefaultRecommendationsService( DbFilmStorage dbFilmStorage) {

    this.dbFilmStorage = dbFilmStorage;
}
@Override
    public List<Film> getRecommendationsFilms(Long idUser){
    return dbFilmStorage.getRecommendationsFilms(idUser);
    }

}

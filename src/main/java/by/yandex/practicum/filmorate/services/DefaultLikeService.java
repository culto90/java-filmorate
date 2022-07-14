package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import by.yandex.practicum.filmorate.exceptions.LikeNotFoundException;
import by.yandex.practicum.filmorate.exceptions.LikeServiceException;
import by.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import by.yandex.practicum.filmorate.storages.LikeStorage;
import by.yandex.practicum.filmorate.storages.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultLikeService implements LikeService {
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public DefaultLikeService(LikeStorage likeStorage, UserStorage userStorage, FilmStorage filmStorage) {
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    @Override
    public Like addLike(Long filmId, Long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);

        if (film == null) {
            throw new FilmNotFoundException("Film with id = '" + filmId + "' not found.");
        }

        if (user == null) {
            throw new UserNotFoundException("User with id = '" + userId + "' not found.");
        }

        Like like = findLike(filmId, userId);

        if (like != null) {
            throw new LikeServiceException("Like already exists.");
        }

        like = likeStorage.put(new Like(film, user));
        film.addLike(like);
        user.addLike(like);
        filmStorage.put(film);
        userStorage.put(user);
        log.info("Added new like: {}", like);
        return like;
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);

        if (film == null) {
            throw new FilmNotFoundException("Film with id = '" + filmId + "' not found.");
        }

        if (user == null) {
            throw new UserNotFoundException("User with id = '" + userId + "' not found.");
        }

        Like like = findLike(filmId, userId);

        if (like == null) {
            throw new LikeNotFoundException("Like is not found.");
        }

        film.removeLike(like);
        user.removeLike(like);
        likeStorage.remove(like);
        log.info("Removed like: {}", like);
    }

    public Like findLike(Long filmId, Long userId) {
        return likeStorage.getAll()
                .stream()
                .filter(like -> like.getFilm().getId().equals(filmId)
                        && like.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);
    }
}
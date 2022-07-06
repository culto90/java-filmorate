package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.LikeNotFoundException;
import by.yandex.practicum.filmorate.exceptions.LikeServiceException;
import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.storages.LikeStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultLikeService implements LikeService {
    private final LikeStorage likeStorage;
    private final UserService userService;
    private final FilmService filmService;

    @Autowired
    public DefaultLikeService(LikeStorage likeStorage, UserService userService, FilmService filmService) {
        this.likeStorage = likeStorage;
        this.userService = userService;
        this.filmService = filmService;
    }

    @Override
    public Like addLike(Long filmId, Long userId) {
        Film film = filmService.getFilmById(filmId);
        User user = userService.getUserById(userId);

        if (film == null) {
            throw new LikeServiceException("Film cannot be null.");
        }

        if (user == null) {
            throw new LikeServiceException("User cannot be null.");
        }

        Like like = findLike(filmId, userId);

        if (like != null) {
            throw new LikeServiceException("Like already exists.");
        }

        like = likeStorage.put(new Like(film, user));
        film.addLike(like);
        user.addLike(like);
        filmService.updateFilm(film);
        userService.updateUser(user);
        log.info("Added new like: {}", like);
        return like;
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        Film film = filmService.getFilmById(filmId);
        User user = userService.getUserById(userId);

        if (film == null) {
            throw new LikeServiceException("Film cannot be null.");
        }

        if (user == null) {
            throw new LikeServiceException("User cannot be null.");
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

package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.models.Like;

public interface LikeService {
    Like addLike(Long filmId, Long userId);
    void removeLike(Long filmId, Long userId);
}

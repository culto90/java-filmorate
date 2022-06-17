package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.serializers.LikeDtoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = LikeDtoSerializer.class)
public class LikeDto {
    private Long id;
    private Film film;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

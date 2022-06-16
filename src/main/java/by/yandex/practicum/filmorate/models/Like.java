package by.yandex.practicum.filmorate.models;

import lombok.ToString;

@ToString
public class Like {
    private Long id;
    private Film film;
    private User user;

    public Like(Film film, User user) {
        this.film = film;
        this.user = user;
    }

    public Like(Long id, Film film, User user) {
        this.id = id;
        this.film = film;
        this.user = user;
    }

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

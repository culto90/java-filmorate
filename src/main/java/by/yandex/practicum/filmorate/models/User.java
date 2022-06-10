package by.yandex.practicum.filmorate.models;

import lombok.ToString;

import java.time.LocalDate;

@ToString
public class User {
    private long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    public User() {

    }

    public User (long id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            this.name = login;
        } else {
            this.name = name;
        }
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}

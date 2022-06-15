package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.rest.serializers.FriendshipDtoSerializer;
import by.yandex.practicum.filmorate.rest.serializers.UserDtoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = UserDtoSerializer.class)
public class UserDto {
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    private long id;
    @Email(message = "Email is not valid.")
    private String email;
    @NotBlank(message = "Login must not be blank.")
    @Pattern(regexp = "^([\\w]*)$", message = "Login must contain only characters and numbers.")
    private String login;
    private String name;
    @Past(message = "Birthday must be a past date.")
    private LocalDate birthday;
    private List<Friendship> friendships;

    public UserDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Friendship> getFriendships() {
        return new ArrayList<Friendship>(friendships);
    }

    public void setFriendships(List<Friendship> friendships) {
        this.friendships = new ArrayList<>(friendships);
    }
}

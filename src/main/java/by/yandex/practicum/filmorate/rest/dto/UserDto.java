package by.yandex.practicum.filmorate.rest.dto;

import javax.validation.constraints.*;
import java.time.LocalDate;

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
}

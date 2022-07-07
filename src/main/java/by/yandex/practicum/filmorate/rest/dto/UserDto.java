package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.rest.serializers.UserDtoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonSerialize(using = UserDtoSerializer.class)
public class UserDto {
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    private long id;
    @NotBlank(message = "Email is not valid.")
    @Email(message = "Email is not valid.")
    private String email;
    @NotBlank(message = "Login must not be blank.")
    @Pattern(regexp = "^([\\w]*)$", message = "Login must contain only characters and numbers.")
    private String login;
    private String name;
    @Past(message = "Birthday must be a past date.")
    private LocalDate birthday;
    private List<Friendship> friendships;
    private List<Like> likes;
}

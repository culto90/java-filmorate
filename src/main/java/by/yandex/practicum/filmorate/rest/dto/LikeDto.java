package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private Long id;
    private Film film;
    private User user;
}

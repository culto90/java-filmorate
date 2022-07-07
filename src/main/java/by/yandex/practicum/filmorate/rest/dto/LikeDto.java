package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.serializers.LikeDtoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize(using = LikeDtoSerializer.class)
public class LikeDto {
    private Long id;
    private Film film;
    private User user;
}

package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.FriendshipStatus;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.serializers.FriendshipDtoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize(using = FriendshipDtoSerializer.class)
public class FriendshipDto {
    private Long id;
    private FriendshipStatus status;
    private User user;
    private User friend;
}

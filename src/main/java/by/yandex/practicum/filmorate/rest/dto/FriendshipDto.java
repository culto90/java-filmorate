package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.FriendshipStatus;
import by.yandex.practicum.filmorate.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDto {
    private Long id;
    private FriendshipStatus status;
    private User user;
    private User friend;
}

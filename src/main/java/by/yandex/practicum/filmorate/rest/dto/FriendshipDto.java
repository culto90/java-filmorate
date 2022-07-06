package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.FriendshipStatus;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.serializers.FriendshipDtoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = FriendshipDtoSerializer.class)
public class FriendshipDto {
    private Long id;
    private FriendshipStatus status;
    private User user;
    private User friend;

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }


}

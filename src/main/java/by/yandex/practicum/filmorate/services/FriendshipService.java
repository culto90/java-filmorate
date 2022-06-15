package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.models.Friendship;
import java.util.List;

public interface FriendshipService {
    List<Friendship> getFriendList(Long userId);
    List<Friendship> getSharedFriends(Long userId, Long otherId);
    Friendship addToFriend(Long userId, Long friendId);
    void removeFriend(Long userId, Long friendId);

}

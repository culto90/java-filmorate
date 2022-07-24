package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FriendshipNotFoundException;
import by.yandex.practicum.filmorate.exceptions.FriendshipServiceException;
import by.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.storages.FriendshipStorage;
import by.yandex.practicum.filmorate.storages.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultFriendshipService implements FriendshipService {
    private final FriendshipStorage friendshipStorage;
    private final UserStorage userStorage;

    @Autowired
    public DefaultFriendshipService(FriendshipStorage friendshipStorage, UserStorage userStorage) {
        this.friendshipStorage = friendshipStorage;
        this.userStorage = userStorage;
    }

    @Override
    public Friendship addToFriend(Long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        if (user == null) {
            throw new UserNotFoundException("User with id = '" + userId + "' not found.");
        }

        if (friend == null) {
            throw new UserNotFoundException("Friend with id = '" + userId + "' not found.");
        }

        if (user.equals(friend)) {
            throw new FriendshipServiceException("Cannot add yourself as friend.");
        }

        List<Friendship> friendships = findFriendships(userId, friendId);

        if (!friendships.isEmpty()) {
            throw new FriendshipServiceException("This user already has this friend.");
        }

        Friendship friendshipForUser = friendshipStorage.put(new Friendship(user, friend));
        user.addFriendship(friendshipForUser);
        log.info("Added new friendship: {}", friendshipForUser);
        return friendshipForUser;
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        User user = userStorage.getById(userId);
        User friend = userStorage.getById(friendId);

        if (user == null) {
            throw new UserNotFoundException("User with id = '" + userId + "' not found.");
        }

        if (friend == null) {
            throw new UserNotFoundException("Friend with id = '" + userId + "' not found.");
        }

        if (user.equals(friend)) {
            throw new FriendshipServiceException("Cannot remove yourself as friend.");
        }

        List<Friendship> friendships = findFriendships(userId, friendId);

        if (friendships.isEmpty()) {
            throw new FriendshipNotFoundException("Friendship is not found.");
        }

        try {
            for (Friendship friendship: friendships) {
                if (user.getFriendshipById(friendship.getId()) != null) {
                    user.removeFriendship(friendship);
                    userStorage.put(user);
                }
                log.info("Removed friendship: {}", friendship);
            }
            friendships.stream().map(friendshipStorage::remove);
        } catch (UserServiceException e) {
            throw new FriendshipNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<User> getFriendList(Long userId) {
        if (userStorage.getById(userId) == null) {
            throw new UserNotFoundException("User with id = '" + userId + "' not found.");
        }
        List<Friendship> friendships = friendshipStorage.getAll();
        return friendships.stream()
                .filter(f -> f.getUser().getId().equals(userId))
                .map(Friendship::getFriend)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getSharedFriends(Long userId, Long otherId) {
        User user = userStorage.getById(userId);
        User other = userStorage.getById(otherId);

        List<User> userFriends = user.getFriendships()
                .stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toList());

        List<User> otherFriends = other.getFriendships()
                .stream()
                .map(Friendship::getFriend)
                .collect(Collectors.toList());

        userFriends.retainAll(otherFriends);
        return userFriends;
    }

    private List<Friendship> findFriendships(Long userId, Long friendId) {
        List<Friendship> friendships = friendshipStorage.getAll();
        return friendships.stream()
                .filter(f -> (f.getUser().getId().equals(userId) && f.getFriend().getId().equals(friendId)))
                .collect(Collectors.toList());
    }
}
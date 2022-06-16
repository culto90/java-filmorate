package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.FriendshipNotFoundException;
import by.yandex.practicum.filmorate.exceptions.FriendshipServiceException;
import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.storages.FriendshipStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InMemoryFriendshipService implements FriendshipService {
    private final UserService userService;
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public InMemoryFriendshipService(UserService userService, FriendshipStorage friendshipStorage) {
        this.userService = userService;
        this.friendshipStorage = friendshipStorage;
    }

    @Override
    public Friendship addToFriend(Long userId, Long friendId) {
        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);

        if (user == null) {
            throw new FriendshipServiceException("User cannot be null.");
        }

        if (friend == null) {
            throw new FriendshipServiceException("Friend cannot be null.");
        }

        if (user.equals(friend)) {
            throw new FriendshipServiceException("Cannot add yourself as friend.");
        }

        List<Friendship> friendships = findFriendships(userId, friendId);

        if (!friendships.isEmpty()) {
            throw new FriendshipServiceException("This user already has this friend.");
        }

        Friendship friendshipForUser = friendshipStorage.put(new Friendship(user, friend));
        Friendship friendshipForFriend = friendshipStorage.put(new Friendship(friend, user));
        user.addFriendship(friendshipForUser);
        friend.addFriendship(friendshipForFriend);
        List<Friendship> createdFriendships = new ArrayList<>();
        createdFriendships.add(friendshipForUser);
        createdFriendships.add(friendshipForFriend);
        log.info("Added new friendship: {}", friendshipForUser);
        return friendshipForUser;
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        User user = userService.getUserById(userId);
        User friend = userService.getUserById(friendId);

        if (user == null) {
            throw new FriendshipServiceException("User cannot be null.");
        }

        if (friend == null) {
            throw new FriendshipServiceException("Friend cannot be null.");
        }

        if (user.equals(friend)) {
            throw new FriendshipServiceException("Cannot add yourself as friend.");
        }

        List<Friendship> friendships = findFriendships(userId, friendId);

        if (friendships.isEmpty()) {
            throw new FriendshipNotFoundException("Friendship is not found.");
        }

        try {
            for (Friendship friendship: friendships) {
                if (user.getFriendshipById(friendship.getId()) != null) {
                    user.removeFriendship(friendship);
                    userService.updateUser(user);
                }

                if (friend.getFriendshipById(friendship.getId()) != null) {
                    friend.removeFriendship(friendship);
                    userService.updateUser(friend);
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
        List<Friendship> friendships = friendshipStorage.getAll();
        return friendships.stream()
                .filter(f -> f.getUser().getId() == userId)
                .map(Friendship::getFriend)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getSharedFriends(Long userId, Long otherId) {
        User user = userService.getUserById(userId);
        User other = userService.getUserById(otherId);

        List<User> userFriends = user.getFriendships()
                .stream()
                .map(f -> f.getFriend())
                .collect(Collectors.toList());
        List<User> otherFriends = other.getFriendships()
                .stream()
                .map(f -> f.getFriend())
                .collect(Collectors.toList());

        userFriends.retainAll(otherFriends);

        return userFriends;
    }

    private List<Friendship> findFriendships(Long userId, Long friendId) {
        List<Friendship> friendships = friendshipStorage.getAll();
        return friendships.stream()
                .filter(f -> (f.getUser().getId() == userId && f.getFriend().getId() == friendId)
                    || (f.getUser().getId() == friendId && f.getFriend().getId() == userId))
                .collect(Collectors.toList());
    }


}

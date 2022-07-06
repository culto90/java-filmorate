package by.yandex.practicum.filmorate.dao;

import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.models.FriendshipStatus;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.storages.FriendshipStorage;
import by.yandex.practicum.filmorate.storages.UserStorage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbFriendshipStorageTest {
    private final FriendshipStorage friendshipStorage;
    private final UserStorage userStorage;

    @Test
    public void testGetAll() {
        User user = new User();
        user.setEmail("user@email.by");
        user.setLogin("user_login");
        user.setName("user_name");
        user.setBirthday(LocalDate.now());

        User friend = new User();
        friend.setEmail("friend@email.by");
        friend.setLogin("friend_login");
        friend.setName("friend_name");
        friend.setBirthday(LocalDate.now());
        userStorage.put(user);
        userStorage.put(friend);

        Friendship friendship = new Friendship(user, friend);
        friendshipStorage.put(friendship);
        int friendshipsCount = friendshipStorage.getAll().size();
        assertTrue(friendshipsCount > 0);
    }

    @Test
    public void testGetById() {
        User user = new User();
        user.setEmail("user@email.by");
        user.setLogin("user_login");
        user.setName("user_name");
        user.setBirthday(LocalDate.now());

        User friend = new User();
        friend.setEmail("friend@email.by");
        friend.setLogin("friend_login");
        friend.setName("friend_name");
        friend.setBirthday(LocalDate.now());
        userStorage.put(user);
        userStorage.put(friend);
        Friendship friendship = new Friendship(user, friend);
        friendship = friendshipStorage.put(friendship);

        Friendship insertedFriendship = friendshipStorage.getById(friendship.getId());
        assertEquals(friendship.getId(), insertedFriendship.getId());
    }

    @Test
    public void testPutInsert() {
        User user = userStorage.getById(1L);
        User friend = userStorage.getById(2L);
        Friendship friendship = new Friendship(user, friend);
        Friendship insertedFriendship = friendshipStorage.put(friendship);
        assertEquals(2L, insertedFriendship.getId());
    }

    @Test
    public void testPutUpdate() {
        User user = new User();
        user.setEmail("user@email.by");
        user.setLogin("user_login");
        user.setName("user_name");
        user.setBirthday(LocalDate.now());

        User friend = new User();
        friend.setEmail("friend@email.by");
        friend.setLogin("friend_login");
        friend.setName("friend_name");
        friend.setBirthday(LocalDate.now());
        user = userStorage.put(user);
        friend = userStorage.put(friend);

        Friendship friendship = friendshipStorage.put(new Friendship(user, friend));
        friendship.setStatus(FriendshipStatus.CONFIRMED);
        Friendship updatedFriendship = friendshipStorage.put(friendship);
        assertEquals(friendship.getStatus(), updatedFriendship.getStatus());
    }

    @Test
    public void testRemove() {
        User user = new User();
        user.setEmail("user@email.by");
        user.setLogin("user_login");
        user.setName("user_name");
        user.setBirthday(LocalDate.now());

        User friend = new User();
        friend.setEmail("friend@email.by");
        friend.setLogin("friend_login");
        friend.setName("friend_name");
        friend.setBirthday(LocalDate.now());
        user = userStorage.put(user);
        friend = userStorage.put(friend);

        Friendship friendship = friendshipStorage.put(new Friendship(user, friend));
        Friendship friendship2 = friendshipStorage.put(new Friendship(friend, user));
        int numberOfFriendshipsBefore = friendshipStorage.getAll().size();
        friendshipStorage.remove(friendship);
        int numberOfFriendshipsAfter = friendshipStorage.getAll().size();
        assertEquals(1, numberOfFriendshipsBefore - numberOfFriendshipsAfter);
    }

    @Test
    public void testRemoveAll() {
        User user = new User();
        user.setEmail("user@email.by");
        user.setLogin("user_login");
        user.setName("user_name");
        user.setBirthday(LocalDate.now());

        User friend = new User();
        friend.setEmail("friend@email.by");
        friend.setLogin("friend_login");
        friend.setName("friend_name");
        friend.setBirthday(LocalDate.now());
        user = userStorage.put(user);
        friend = userStorage.put(friend);

        Friendship friendship = friendshipStorage.put(new Friendship(user, friend));
        Friendship friendship2 = friendshipStorage.put(new Friendship(friend, user));
        int numberOfFriendshipsBefore = friendshipStorage.getAll().size();
        assertTrue(numberOfFriendshipsBefore > 1);
        friendshipStorage.removeAll();
        int numberOfFriendshipsAfter = friendshipStorage.getAll().size();
        assertEquals(0, numberOfFriendshipsAfter);
    }
}

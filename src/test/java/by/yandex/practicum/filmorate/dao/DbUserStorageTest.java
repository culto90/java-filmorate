package by.yandex.practicum.filmorate.dao;

import by.yandex.practicum.filmorate.models.User;
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
public class DbUserStorageTest {
    private final UserStorage userStorage;

    @Test
    public void getAllUsers() {
        User user = new User();
        user.setEmail("insert@email.by");
        user.setLogin("insert_login");
        user.setName("insert_name");
        user.setBirthday(LocalDate.now());
        userStorage.put(user);
        int usersCount = userStorage.getAll().size();
        assertTrue(usersCount > 0);
    }

    @Test
    public void getUserById() {
        User foundUser = userStorage.getById(1L);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    public void testPutInsert() {
        User user = new User();
        user.setEmail("insert@email.by");
        user.setLogin("insert_login");
        user.setName("insert_name");
        user.setBirthday(LocalDate.now());

        User insertedUser = userStorage.put(user);
        assertEquals(user.getEmail(), insertedUser.getEmail());
        assertEquals(user.getLogin(), insertedUser.getLogin());
        assertEquals(user.getName(), insertedUser.getName());
        assertEquals(user.getBirthday(), insertedUser.getBirthday());
    }

    @Test
    public void testPutUpdate() {
        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());
        User user = userStorage.put(insertedUser);

        user.setEmail("updated@email.by");
        user.setLogin("updated_login");
        user.setName("updated_name");
        user.setBirthday(LocalDate.of(1990, 05, 16));

        User updatedUser = userStorage.put(user);

        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getLogin(), updatedUser.getLogin());
        assertEquals(user.getName(), updatedUser.getName());
        assertEquals(user.getBirthday(), updatedUser.getBirthday());
    }

    @Test
    public void testRemove() {
        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());
        userStorage.put(insertedUser);

        User insertedUser2 = new User();
        insertedUser2.setEmail("insert2@email.by");
        insertedUser2.setLogin("insert2_login");
        insertedUser2.setName("insert2_name");
        insertedUser2.setBirthday(LocalDate.now());
        userStorage.put(insertedUser2);

        int numberOfUsersBefore = userStorage.getAll().size();
        assertTrue(numberOfUsersBefore > 0);
        userStorage.remove(insertedUser);
        int numberOfUsersAfter = userStorage.getAll().size();
        assertEquals(1, numberOfUsersBefore - numberOfUsersAfter);
    }

    @Test
    public void testRemoveAll() {
        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());
        userStorage.put(insertedUser);

        User insertedUser2 = new User();
        insertedUser2.setEmail("insert2@email.by");
        insertedUser2.setLogin("insert2_login");
        insertedUser2.setName("insert2_name");
        insertedUser2.setBirthday(LocalDate.now());
        userStorage.put(insertedUser2);

        int numberOfUsers = userStorage.getAll().size();
        assertTrue(numberOfUsers > 0);
        userStorage.removeAll();
        numberOfUsers = userStorage.getAll().size();
        assertEquals(0, numberOfUsers);
    }

    @Test
    public void testGetByEmail() {
        User insertedUser = new User();
        insertedUser.setEmail("getEmail@email.by");
        insertedUser.setLogin("getEmail");
        insertedUser.setName("getEmail_name");
        insertedUser.setBirthday(LocalDate.now());
        userStorage.put(insertedUser);
        User foundUser = userStorage.getByEmail(insertedUser.getEmail());

        assertEquals(insertedUser.getEmail(), foundUser.getEmail());
        assertEquals(insertedUser.getLogin(), foundUser.getLogin());
        assertEquals(insertedUser.getName(), foundUser.getName());
        assertEquals(insertedUser.getBirthday(), foundUser.getBirthday());
    }
}

package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.storages.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class DefaultUserService implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public DefaultUserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    @Override
    public User getUserById(long id) throws UserNotFoundException {
        User user = userStorage.getById(id);
        if (user == null) {
            throw new UserNotFoundException("User with id = '" + id + "' not found.");
        }
        return user;
    }

    @Override
    public User addUser(User newUser) throws UserServiceException {
        if (newUser == null) {
            throw new UserServiceException("Cannot add empty user.");
        }

        Long id = newUser.getId();
        if (id.equals(0L)) {
            if (userStorage.getById(id) != null) {
                throw new UserServiceException("User with this id already exists.");
            }
        }

        if (userStorage.getByEmail(newUser.getEmail()) != null) {
            throw new UserServiceException("User with same login/email already exists.");
        }

        User addedUser = userStorage.put(newUser);
        log.info("User added: {}", addedUser);
        return addedUser;
    }

    @Override
    public User updateUser(User updatedUser) throws UserServiceException {
        if (updatedUser == null) {
            throw new UserServiceException("Cannot update empty user.");
        }

        Long id = updatedUser.getId();
        if (id == null) {
            throw new UserServiceException("Cannot update user with empty id.");
        }

        User foundUser = userStorage.getById(id);
        if (foundUser == null) {
            throw new UserNotFoundException("User for update is not found.");
        }

        if (isHaveUserWithSameLoginEmail(updatedUser)) {
            throw new UserServiceException("User with same login/email already exists.");
        }

        log.info("Updated user: old value: {}", foundUser);
        foundUser.setEmail(updatedUser.getEmail());
        foundUser.setName(updatedUser.getName());
        foundUser.setLogin(updatedUser.getLogin());
        foundUser.setBirthday(updatedUser.getBirthday());
        foundUser.setFriendshipList(updatedUser.getFriendships());
        log.info("Updated user: new value: {}", foundUser);

        return userStorage.put(foundUser);
    }

    private boolean isHaveUserWithSameLoginEmail(User user) {
        List<User> users = userStorage.getAll();
        Long userid = user.getId();
        String userLogin = user.getLogin();
        String userEmail = user.getEmail();

        User foundUser = users.stream()
                .filter(u -> (u.getLogin().equals(userLogin)
                        || u.getEmail().equals(userEmail))
                        && !u.getId().equals(userid))
                .findFirst()
                .orElse(null);

        return foundUser != null;
    }
}

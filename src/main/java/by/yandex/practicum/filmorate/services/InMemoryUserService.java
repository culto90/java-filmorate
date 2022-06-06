package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class InMemoryUserService implements UserService {
    private static final AtomicLong atomicLong = new AtomicLong(1);
    private final Map<Long, User> users;

    public InMemoryUserService() {
        users = new HashMap<>();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findUserById(long id) {
        return users.get(id);
    }

    @Override
    public User addUser(User newUser) throws UserServiceException {
        if (users.containsKey(newUser.getId())) {
            throw new UserServiceException("User with this id already exists.");
        }

        if (findUserWithSameLoginEmail(newUser) != null) {
            throw new UserServiceException("User with same login/email already exists.");
        }

        long id = atomicLong.getAndIncrement();
        User user = new User(id, newUser.getEmail(), newUser.getLogin(), newUser.getName(), newUser.getBirthday());
        users.put(id, user);
        log.info("Added new user: " + user.toString());
        return user;
    }

    @Override
    public User updateUser(User newUser) throws UserServiceException {
        User foundUser = users.get(newUser.getId());
        if (foundUser == null) {
            throw new UserServiceException("User with this id is not found.");
        }

        if (findUserWithSameLoginEmail(newUser) != null) {
            throw new UserServiceException("User with same login/email already exists.");
        }
        log.info("Updated user: old value: " + foundUser.toString());

        foundUser.setEmail(newUser.getEmail());
        foundUser.setName(newUser.getName());
        foundUser.setLogin(newUser.getLogin());
        foundUser.setBirthday(newUser.getBirthday());
        users.put(foundUser.getId(), foundUser);

        log.info("Updated user: new value: " + foundUser.toString());
        
        return foundUser;
    }

    private User findUserWithSameLoginEmail(User user) {
        long userid = user.getId();
        String userLogin = user.getLogin();
        String userEmail = user.getEmail();

        Optional<User> foundUser = users.values().stream()
                .filter(u -> (u.getLogin().equals(userLogin)
                        || u.getEmail().equals(userEmail))
                        && u.getId() != userid)
                .findFirst();
        return foundUser.orElse(null);
    }

}

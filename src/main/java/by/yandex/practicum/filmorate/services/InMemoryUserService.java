package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryUserService implements UserService {
    private static AtomicLong atomicLong = new AtomicLong(1);
    private Map<Long, User> users;

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

        foundUser.setEmail(newUser.getEmail());
        foundUser.setName(newUser.getName());
        foundUser.setLogin(newUser.getLogin());
        foundUser.setBirthday(newUser.getBirthday());
        users.put(foundUser.getId(), foundUser);
        
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

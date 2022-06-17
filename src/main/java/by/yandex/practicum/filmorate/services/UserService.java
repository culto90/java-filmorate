package by.yandex.practicum.filmorate.services;

import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(long id);
    User addUser(User user) throws UserServiceException;
    User updateUser(User user) throws UserServiceException;
}

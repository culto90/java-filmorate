package by.yandex.practicum.filmorate.storages;

import by.yandex.practicum.filmorate.models.User;

public interface UserStorage extends Storage<User, Long> {
    User getByEmail(String email);
}

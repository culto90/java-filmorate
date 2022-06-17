package by.yandex.practicum.filmorate.storages;

import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.services.id_generators.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final IdGeneratorService idGenerator;
    private final Map<Long, User> users;

    @Autowired
    public InMemoryUserStorage(IdGeneratorService idGenerator) {
        this.idGenerator = idGenerator;
        this.users = new HashMap<>();
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getById(Long id) {
        return users.values()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return users.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User put(User newUser) {
        Long id = newUser.getId();
        if (id == null || id.equals(0L)) {
            newUser.setId((Long) idGenerator.getUserId());
        }
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public User remove(User user) {
        users.remove(user.getId());
        return user;
    }

    @Override
    public void removeAll() {
        users.clear();
    }
}

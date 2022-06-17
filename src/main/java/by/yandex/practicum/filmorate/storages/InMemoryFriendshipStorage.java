package by.yandex.practicum.filmorate.storages;

import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.services.id_generators.IdGeneratorService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFriendshipStorage implements FriendshipStorage {
    private final IdGeneratorService idGenerator;
    private final Map<Long, Friendship> friendships;

    public InMemoryFriendshipStorage(Map<Long, Friendship> friendships, IdGeneratorService idGenerator) {
        this.idGenerator = idGenerator;
        this.friendships = new HashMap<>();
    }

    @Override
    public List<Friendship> getAll() {
        return new ArrayList<>(friendships.values());
    }

    @Override
    public Friendship getById(Long id) {
        return friendships.values()
                .stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Friendship put(Friendship newFriendship) {
        Long id = newFriendship.getId();
        if (id == null || id.equals(0L)) {
            newFriendship.setId((Long) idGenerator.getFriendshipId());
        }
        friendships.put(newFriendship.getId(), newFriendship);
        return newFriendship;
    }

    @Override
    public Friendship remove(Friendship friendship) {
        friendships.remove(friendship.getId());
        return friendship;
    }

    @Override
    public void removeAll() {
        friendships.clear();
    }
}

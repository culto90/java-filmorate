package by.yandex.practicum.filmorate.storages;

import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.services.id_generators.IdGeneratorService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryLikeStorage implements LikeStorage {
    private final IdGeneratorService idGenerator;
    Map<Long, Like> likes;


    public InMemoryLikeStorage(IdGeneratorService idGenerator) {
        this.idGenerator = idGenerator;
        this.likes = new HashMap<>();
    }


    @Override
    public List<Like> getAll() {
        return new ArrayList<>(likes.values());
    }

    @Override
    public Like getById(Long id) {
        return likes.values()
                .stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Like put(Like like) {
        Long id = like.getId();
        if (id == null || id == 0) {
            like.setId((Long) idGenerator.getLikeId());
        }
        likes.put(like.getId(), like);
        return like;
    }

    @Override
    public Like remove(Like like) {
        likes.remove(like.getId());
        return like;
    }

    @Override
    public void removeAll() {
        likes.clear();
    }
}

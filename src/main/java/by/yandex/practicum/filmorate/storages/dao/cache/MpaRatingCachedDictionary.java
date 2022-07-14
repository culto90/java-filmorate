package by.yandex.practicum.filmorate.storages.dao.cache;

import by.yandex.practicum.filmorate.models.MpaRating;
import by.yandex.practicum.filmorate.storages.MpaRatingStorage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MpaRatingCachedDictionary implements CachedDictionary<MpaRating> {
    private Map<Long, MpaRating> dictionaries;
    private final MpaRatingStorage storage;

    public MpaRatingCachedDictionary(MpaRatingStorage storage) {
        this.storage = storage;
        this.init();
    }

    private void init() {
        dictionaries = storage.getAll()
                .stream()
                .collect(Collectors.toMap(MpaRating::getId, rating -> rating));
    }

    @Override
    public boolean isExists(MpaRating rating) {
        if (rating == null) {
            return false;
        }
        return dictionaries.containsKey(rating.getId());
    }
}
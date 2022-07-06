package by.yandex.practicum.filmorate.storages.dao.cache;

import by.yandex.practicum.filmorate.models.dictionaries.Dictionary;
import by.yandex.practicum.filmorate.models.dictionaries.MpaRating;
import by.yandex.practicum.filmorate.storages.MpaRatingStorage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MpaRatingCachedDictionary implements CachedDictionary<MpaRating>{
    private Map<Long, Dictionary> dictionaries;
    private final MpaRatingStorage storage;

    public MpaRatingCachedDictionary(MpaRatingStorage storage) {
        this.storage = storage;
        this.init();
    }

    private void init() {
        dictionaries = storage.getAll()
                .stream()
                .collect(Collectors.toMap(MpaRating::getId, d->d));
    }

    @Override
    public boolean isExists(MpaRating rating) {
        if (rating == null) {
            return false;
        }
        return dictionaries.containsKey(rating.getId());
    }
}

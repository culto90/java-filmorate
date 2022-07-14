package by.yandex.practicum.filmorate.storages.dao.cache;

import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.storages.GenreStorage;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GenreCachedDictionary implements CachedDictionary<Genre> {
    private Map<Long, Genre> dictionaries;
    private final GenreStorage storage;

    public GenreCachedDictionary(GenreStorage storage) {
        this.storage = storage;
        init();
    }

    private void init() {
        dictionaries = storage.getAll()
                .stream()
                .collect(Collectors.toMap(Genre::getId, genre -> genre));
    }

    @Override
    public boolean isExists(Genre genre) {
        if (genre == null) {
            return false;
        }
        return dictionaries.containsKey(genre.getId());
    }
}
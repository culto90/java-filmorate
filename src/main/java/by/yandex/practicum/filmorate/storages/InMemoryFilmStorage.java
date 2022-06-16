package by.yandex.practicum.filmorate.storages;

import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.services.id_generators.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final IdGeneratorService idGenerator;
    private final Map<Long, Film> films;

    @Autowired
    public InMemoryFilmStorage(IdGeneratorService idGenerator) {
        this.idGenerator = idGenerator;
        films = new HashMap<>();
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getById(Long id) {
        return films.values()
                .stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Film put(Film newFilm) {
        Long id = newFilm.getId();
        if (id == null || id == 0) {
            newFilm.setId((Long) idGenerator.getFilmId());
        }
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @Override
    public Film remove(Film film) {
        films.remove(film.getId());
        return film;
    }

    @Override
    public void removeAll() {
        films.clear();
    }
}

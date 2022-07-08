package by.yandex.practicum.filmorate.dao;

import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbFilmStorageTest {
    private final FilmStorage filmStorage;

    @Test
    public void testGetAll() {
        Film film = new Film();
        film.setName("New film");
        film.setDescription("New film description");
        film.setReleaseDate(LocalDate.of(2020, 9, 9));
        film.setDuration(80);
        film.setRate(0);
        film.setRating(null);
        filmStorage.put(film);
        int filmsCount = filmStorage.getAll().size();
        assertTrue(filmsCount > 0);
    }

    @Test
    public void testGetById() {
        Film film = new Film();
        film.setName("New film");
        film.setDescription("New film description");
        film.setReleaseDate(LocalDate.of(2020, 9, 9));
        film.setDuration(80);
        film.setRate(0);
        film.setRating(null);
        film = filmStorage.put(film);
        Film inserted = filmStorage.getById(film.getId());
        assertEquals(film.getId(), inserted.getId());
    }

    @Test
    public void testPutInsert() {
        Film film = new Film();
        film.setName("New film");
        film.setDescription("New film description");
        film.setReleaseDate(LocalDate.of(2020, 9, 9));
        film.setDuration(80);
        film.setRate(0);
        film.setRating(null);
        Film insertedFilm = filmStorage.put(film);
        assertEquals(2L, insertedFilm.getId());
        assertEquals(film.getName(), insertedFilm.getName());
        assertEquals(film.getDescription(), insertedFilm.getDescription());
        assertEquals(film.getReleaseDate(), insertedFilm.getReleaseDate());
        assertEquals(film.getDuration(), insertedFilm.getDuration());
        assertEquals(film.getRate(), insertedFilm.getRate());
    }

    @Test
    public void testPutUpdate() {
        Film film = new Film();
        film.setName("New film");
        film.setDescription("New film description");
        film.setReleaseDate(LocalDate.of(2020, 9, 9));
        film.setDuration(80);
        film.setRate(0);
        film.setRating(null);

        Film inserted = filmStorage.put(film);

        inserted.setName("Updated film");
        inserted.setDescription("Updated film description");
        inserted.setReleaseDate(LocalDate.of(2022, 9, 9));
        inserted.setDuration(180);
        inserted.setRate(100);
        inserted.setRating(null);

        Film updatedFilm =  filmStorage.put(inserted);

        assertEquals(inserted.getName(), updatedFilm.getName());
        assertEquals(inserted.getDescription(), updatedFilm.getDescription());
        assertEquals(inserted.getReleaseDate(), updatedFilm.getReleaseDate());
        assertEquals(inserted.getDuration(), updatedFilm.getDuration());
        assertEquals(inserted.getRate(), updatedFilm.getRate());
    }

    @Test
    public void testRemove() {
        Film film = new Film();
        film.setName("New film");
        film.setDescription("New film description");
        film.setReleaseDate(LocalDate.of(2020, 9, 9));
        film.setDuration(80);
        film.setRate(0);
        film.setRating(null);
        film = filmStorage.put(film);

        Film film2 = new Film();
        film2.setName("New film2");
        film2.setDescription("New film2 description");
        film2.setReleaseDate(LocalDate.of(2020, 9, 9));
        film2.setDuration(80);
        film2.setRate(12);
        film2.setRating(null);
        film2 = filmStorage.put(film2);

        int numberOfUsersBefore = filmStorage.getAll().size();
        assertTrue(numberOfUsersBefore > 0);
        filmStorage.remove(film);
        int numberOfUsersAfter = filmStorage.getAll().size();
        assertEquals(1, numberOfUsersBefore - numberOfUsersAfter);
    }

    @Test
    public void removeAll() {
        Film film = new Film();
        film.setName("New film");
        film.setDescription("New film description");
        film.setReleaseDate(LocalDate.of(2020, 9, 9));
        film.setDuration(80);
        film.setRate(0);
        film.setRating(null);
        filmStorage.put(film);

        Film film2 = new Film();
        film2.setName("New film2");
        film2.setDescription("New film2 description");
        film2.setReleaseDate(LocalDate.of(2020, 9, 9));
        film2.setDuration(80);
        film2.setRate(12);
        film2.setRating(null);
        filmStorage.put(film2);

        int numberOfUsers = filmStorage.getAll().size();
        assertTrue(numberOfUsers > 0);
        filmStorage.removeAll();
        numberOfUsers = filmStorage.getAll().size();
        assertEquals(0, numberOfUsers);
    }
}

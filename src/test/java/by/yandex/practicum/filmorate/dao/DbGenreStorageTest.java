package by.yandex.practicum.filmorate.dao;

import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.storages.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbGenreStorageTest {
    private final GenreStorage genreStorage;

    @Test
    public void testGetAll() {
        int genresCount = genreStorage.getAll().size();
        assertTrue(genresCount > 0);
    }

    @Test
    public void testGetGenreById() {
        Genre genre = genreStorage.getById(1L);
        assertEquals(1L, genre.getId());
    }

    @Test
    public void testPutInsert() {
        Genre genre = new Genre();
        genre.setName("Thriller");
        genre.setDescription("Films that evoke excitement and suspense in the audience.");
        Genre inserted = genreStorage.put(genre);
        assertEquals(genre.getName(), inserted.getName());
        assertEquals(genre.getDescription(), inserted.getDescription());
    }

    @Test
    public void testPutUpdate() {
        Genre genre = new Genre();
        genre.setName("Bad Historical");
        genre.setDescription("Its very interesting films!.");
        Genre inserted = genreStorage.put(genre);
        inserted.setName("Historical");
        inserted.setDescription("Films that either provide more-or-less accurate representations of historical accounts or depict fictional narratives placed inside an accurate depiction of a historical setting.");
        Genre updated = genreStorage.put(inserted);
        assertEquals(inserted.getId(), updated.getId());
        assertEquals(inserted.getName(), updated.getName());
        assertEquals(inserted.getDescription(), updated.getDescription());
    }

    @Test
    public void testRemove() {
        Genre genre = new Genre();
        genre.setName("Genre For Delete");
        genre.setDescription("Genre For Delete Description");
        genreStorage.put(genre);
        int numberOfGenresBefore = genreStorage.getAll().size();
        genreStorage.remove(genre);
        int numberOfGenresAfter = genreStorage.getAll().size();
        assertEquals(1, numberOfGenresBefore - numberOfGenresAfter);
    }

    @Test
    public void testRemoveAll() {
        Genre genre = new Genre();
        genre.setName("Genre For Delete ALL");
        genre.setDescription("Genre For Delete ALL Description");
        genreStorage.put(genre);
        int numberOfGenres = genreStorage.getAll().size();
        assertTrue(numberOfGenres > 0);
        genreStorage.removeAll();
        numberOfGenres = genreStorage.getAll().size();
        assertEquals(0, numberOfGenres);
    }
}

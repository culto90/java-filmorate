package by.yandex.practicum.filmorate.dao;

import by.yandex.practicum.filmorate.models.dictionaries.MpaRating;
import by.yandex.practicum.filmorate.storages.dao.DbMpaRatingStorage;
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
public class DbMpaRatingStorageTest {
    private final DbMpaRatingStorage ratingStorage;

    @Test
    public void testGetAllRatings() {
        int likesCount = ratingStorage.getAll().size();
        assertTrue(likesCount > 0);
    }

    @Test
    public void testGetByIdRating() {
        MpaRating ratingDictionary = ratingStorage.getById(1L);
        assertEquals(1L, ratingDictionary.getId());
    }

    @Test
    public void testPutInsert() {
        MpaRating rating = new MpaRating();
        rating.setCode("NR");
        rating.setDescription("Not Rated");
        MpaRating inserted = ratingStorage.put(rating);
        assertEquals(rating.getCode(), inserted.getCode());
        assertEquals(rating.getDescription(), inserted.getDescription());
    }

    @Test
    public void testPutUpdate() {
        MpaRating rating = new MpaRating();
        rating.setCode("NR");
        rating.setDescription("Not Rated");
        MpaRating inserted = ratingStorage.put(rating);
        inserted.setCode("UR");
        inserted.setDescription("Unrated");
        MpaRating updated = ratingStorage.put(inserted);
        assertEquals(inserted.getId(), updated.getId());
        assertEquals(inserted.getCode(), updated.getCode());
        assertEquals(inserted.getDescription(), updated.getDescription());
    }

    @Test
    public void testRemove() {
        MpaRating rating = new MpaRating();
        rating.setCode("NR");
        rating.setDescription("Not Rated");
        ratingStorage.put(rating);
        int numberOfRatingBefore = ratingStorage.getAll().size();
        ratingStorage.remove(rating);
        int numberOfRatingAfter = ratingStorage.getAll().size();
        assertEquals(1, numberOfRatingBefore - numberOfRatingAfter);
    }

    @Test
    public void testRemoveAll() {
        MpaRating rating = new MpaRating();
        rating.setCode("NR");
        rating.setDescription("Not Rated");
        ratingStorage.put(rating);
        int numberOfRatings = ratingStorage.getAll().size();
        assertTrue(numberOfRatings > 0);
        ratingStorage.removeAll();
        numberOfRatings = ratingStorage.getAll().size();
        assertEquals(0, numberOfRatings);
    }
}
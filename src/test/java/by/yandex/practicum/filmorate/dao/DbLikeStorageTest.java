package by.yandex.practicum.filmorate.dao;

import by.yandex.practicum.filmorate.models.*;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import by.yandex.practicum.filmorate.storages.LikeStorage;
import by.yandex.practicum.filmorate.storages.UserStorage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbLikeStorageTest {
    private final LikeStorage likeStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Test
    public void testGetAllLikes() {
        Film film = new Film();
        film.setName("New film");
        film.setDescription("New film description");
        film.setReleaseDate(LocalDate.of(2020, 9, 9));
        film.setDuration(80);
        film.setRate(0);
        film.setRating(null);
        film = filmStorage.put(film);

        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());
        insertedUser = userStorage.put(insertedUser);
        Like like = new Like(film, insertedUser);
        likeStorage.put(like);
        int likesCount = likeStorage.getAll().size();
        assertTrue(likesCount > 0);
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

        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());
        insertedUser = userStorage.put(insertedUser);
        Like like = new Like(film, insertedUser);
        likeStorage.put(like);

        Like inserted = likeStorage.getById(1L);
        assertEquals(1L, inserted.getId());
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
        film = filmStorage.put(film);

        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());
        insertedUser = userStorage.put(insertedUser);
        Like like = new Like(film, insertedUser);
        Like inserted = likeStorage.put(like);
        assertEquals(like.getFilm().getId(), inserted.getFilm().getId());
        assertEquals(like.getUser().getId(), inserted.getUser().getId());
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
        film = filmStorage.put(film);

        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());
        insertedUser = userStorage.put(insertedUser);
        Like like = new Like(film, insertedUser);
        Like insertedLike = likeStorage.put(like);

        User user2 = new User();
        user2.setEmail("user2@email.by");
        user2.setLogin("user2_login");
        user2.setName("user2_name");
        user2.setBirthday(LocalDate.now());
        user2 = userStorage.put(user2);
        insertedLike.setUser(user2);
        Like updatedLike = likeStorage.put(insertedLike);
        assertEquals(insertedLike.getFilm().getId(), updatedLike.getFilm().getId());
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

        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());

        insertedUser = userStorage.put(insertedUser);
        User user2 = new User();
        user2.setEmail("user2@email.by");
        user2.setLogin("user2_login");
        user2.setName("user2_name");
        user2.setBirthday(LocalDate.now());
        user2 = userStorage.put(user2);


        Like like = new Like(film, insertedUser);
        like = likeStorage.put(like);
        Like like2 = new Like(film, user2);
        likeStorage.put(like2);

        int numberOfLikesBefore = likeStorage.getAll().size();
        likeStorage.remove(like);
        int numberOfLikesAfter = likeStorage.getAll().size();
        assertEquals(1, numberOfLikesBefore - numberOfLikesAfter);
    }

    @Test
    public void testRemoveAll() {
        Film film = new Film();
        film.setName("New film");
        film.setDescription("New film description");
        film.setReleaseDate(LocalDate.of(2020, 9, 9));
        film.setDuration(80);
        film.setRate(0);
        film.setRating(null);
        film = filmStorage.put(film);

        User insertedUser = new User();
        insertedUser.setEmail("insert@email.by");
        insertedUser.setLogin("insert_login");
        insertedUser.setName("insert_name");
        insertedUser.setBirthday(LocalDate.now());

        insertedUser = userStorage.put(insertedUser);
        User user2 = new User();
        user2.setEmail("user2@email.by");
        user2.setLogin("user2_login");
        user2.setName("user2_name");
        user2.setBirthday(LocalDate.now());
        user2 = userStorage.put(user2);


        Like like = new Like(film, insertedUser);
        likeStorage.put(like);
        Like like2 = new Like(film, user2);
        likeStorage.put(like2);
        int numberOfLikesBefore = likeStorage.getAll().size();
        assertTrue(numberOfLikesBefore > 1);
        likeStorage.removeAll();
        int numberOfLikesAfter = likeStorage.getAll().size();
        assertEquals(0, numberOfLikesAfter);
    }
}

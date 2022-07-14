package by.yandex.practicum.filmorate.storages.dao;

import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.models.MpaRating;
import by.yandex.practicum.filmorate.storages.LikeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Primary
@Repository
public class DbLikeStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL_LIKES = "SELECT like_id, user_id, film_id FROM likes";
    private static final String SELECT_CORRESPONDING_LIKE = "SELECT like_id, user_id, film_id FROM likes WHERE like_id = ?";
    private static final String SELECT_CORRESPONDING_USER = "SELECT user_id, email, login, name, birthday " +
            "FROM users WHERE user_id = ?";
    private static final String SELECT_CORRESPONDING_MPA_RATING = "SELECT mpa_rating_id, code, description " +
            "FROM mpa_ratings WHERE mpa_rating_id = ?";
    private static final String SELECT_CORRESPONDING_FILM = "SELECT film_id, name, description, release_date, " +
            "duration, rate, mpa_rating_id FROM films WHERE film_id = ?";
    private static final String UPDATE_CORRESPONDING_LIKE = "UPDATE likes SET film_id = ?, user_id = ? " +
            "WHERE like_id = ?";
    private static final String DELETE_CORRESPONDING_LIKE = "DELETE FROM likes WHERE like_id = ?";
    private static final String DELETE_ALL_LIKES = "DELETE FROM likes";


    @Autowired
    public DbLikeStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Like> getAll() {
        return jdbcTemplate.query(SELECT_ALL_LIKES, this::mapRowToLike);
    }

    @Override
    public Like getById(Long id) {
        if (id == null) {
            return null;
        }
        Like like;
        try {
            like = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_LIKE, this::mapRowToLike, id);
        } catch (EmptyResultDataAccessException e) {
            like = null;
        }
        return like;
    }

    @Override
    public Like put(Like newLike) {
        Like like = getById(newLike.getId());
        if (like == null) {
            return insertLike(newLike);
        }
        return updateLike(newLike);
    }

    @Override
    public Like remove(Like like) {
        jdbcTemplate.update(DELETE_CORRESPONDING_LIKE, like.getId());
        return like;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(DELETE_ALL_LIKES);
    }

    private Like insertLike(Like like) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("likes")
                .usingGeneratedKeyColumns("like_id");
        Long returnedId = simpleJdbcInsert.executeAndReturnKey(this.likeToMap(like)).longValue();
        if (!returnedId.equals(0L)) {
            like.setId(returnedId);
        }
        return getById(returnedId);
    }

    private Like updateLike(Like like) {
        jdbcTemplate.update(UPDATE_CORRESPONDING_LIKE,
                like.getFilm().getId(),
                like.getUser().getId(),
                like.getId());
        return getById(like.getId());
    }

    private Map<String, Object> likeToMap(Like like) {
        Map<String, Object> values = new HashMap<>();
        values.put("film_id", like.getFilm().getId());
        values.put("user_id", like.getUser().getId());
        return values;
    }

    private Like mapRowToLike(ResultSet resultSet, int rowNum) throws SQLException {
        Long likeId = resultSet.getLong("like_id");
        Long userId = resultSet.getLong("user_id");
        Long filmId = resultSet.getLong("film_id");
        User user = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_USER,  this::mapRowToUser, userId);
        Film film = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_FILM, this::mapRowToFilm, filmId);
        return new Like(likeId, film, user);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getLong("user_id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("name"),
                Objects.requireNonNull(resultSet.getDate("birthday")).toLocalDate());
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(
                resultSet.getLong("film_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("release_date").toLocalDate(),
                resultSet.getInt("duration"),
                resultSet.getDouble("rate")
        );
        Long ratingId = resultSet.getLong("mpa_rating_id");
        if (ratingId != null) {
            if (ratingId.compareTo(0L) > 0) {
                MpaRating rating = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_MPA_RATING
                        , this::mapRowToRating
                        , ratingId);
                film.setRating(rating);
            }
        }
        return film;
    }

    private MpaRating mapRowToRating(ResultSet resultSet, int rowNum) throws SQLException {
        return new MpaRating(
                resultSet.getLong("mpa_rating_id"),
                resultSet.getString("code"),
                resultSet.getString("description")
        );
    }
}

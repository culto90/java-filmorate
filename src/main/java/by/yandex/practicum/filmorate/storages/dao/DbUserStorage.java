package by.yandex.practicum.filmorate.storages.dao;

import by.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import by.yandex.practicum.filmorate.models.*;
import by.yandex.practicum.filmorate.models.dictionaries.MpaRating;
import by.yandex.practicum.filmorate.storages.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Primary
@Slf4j
@Repository
public class DbUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL_USERS = "SELECT user_id, email, login, name, birthday FROM users";
    private static final String SELECT_ALL_FRIENDSHIP_CORRESPONDING_USER = "SELECT friendship_id, user_id, " +
            "friend_id, status FROM friendships WHERE user_id = ?";
    private static final String SELECT_CORRESPONDING_USER = "SELECT user_id, email, login, name, birthday " +
            "FROM users WHERE user_id = ?";
    private static final String DELETE_CORRESPONDING_USER = "DELETE FROM friendships WHERE user_id = ?;" +
            "DELETE FROM likes WHERE like_id = ?;" +
            "DELETE FROM users WHERE user_id = ?;";
    private static final String DELETE_ALL_USERS = "DELETE FROM friendships;" +
            "DELETE FROM likes;" +
            "DELETE FROM users;";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_USER = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? " +
            "WHERE user_id = ?";
    private static final String SELECT_ALL_LIKES_CORRESPONDING_USER = "SELECT like_id, film_id, user_id FROM likes " +
            "WHERE user_id = ?";
    private static final String SELECT_CORRESPONDING_FILM = "SELECT film_id, name, description, release_date, " +
            "duration, rate, mpa_rating_id FROM films WHERE film_id = ?";
    private static final String SELECT_CORRESPONDING_MPA_RATING = "SELECT mpa_rating_id, code, description " +
            "FROM mpa_ratings WHERE mpa_rating_id = ?";
    private static final String DELETE_ALL_FRIENDSHIPS_CORRESPONDING_USER = "DELETE FROM friendships WHERE user_id = ?";

    @Autowired
    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query(SELECT_ALL_USERS, this::mapRowToUser);
        for (User user : users) {
            List<Friendship> friendships = jdbcTemplate.query(SELECT_ALL_FRIENDSHIP_CORRESPONDING_USER,
                    this::mapFriendRowToUser,
                    user.getId());
            user.setFriendshipList(friendships);
            List<Like> likes = jdbcTemplate.query(SELECT_ALL_LIKES_CORRESPONDING_USER,
                    this::mapLikeRowToUser,
                    user.getId());
            user.setLikeList(likes);
        }
        return users;
    }

    @Override
    public User getById(Long id) {
        if (id == null) {
            return null;
        }
        User user;
        try {
            user = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_USER, this::mapRowToUser, id);
        } catch (EmptyResultDataAccessException e) {
            user = null;
        }
        if (user != null) {
            List<Friendship> friendships = jdbcTemplate.query(SELECT_ALL_FRIENDSHIP_CORRESPONDING_USER,
                    this::mapFriendRowToUser,
                    user.getId());
            user.setFriendshipList(friendships);
            List<Like> likes = jdbcTemplate.query(SELECT_ALL_LIKES_CORRESPONDING_USER,
                    this::mapLikeRowToUser,
                    user.getId());
            user.setLikeList(likes);
        }
        return user;
    }

    @Override
    public User put(User newUser) {
        User user = getById(newUser.getId());
        if (user == null) {
            return insertUser(newUser);
        }
        return updateUser(newUser);
    }

    @Override
    public User remove(User user) {
        jdbcTemplate.update(DELETE_CORRESPONDING_USER, user.getId(), user.getId(), user.getId());
        return user;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(DELETE_ALL_USERS);
    }

    @Override
    public User getByEmail(String email) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(SELECT_USER_BY_EMAIL, email);
        if (userRows.next()) {
            log.info("Retrieve User with email = {}", email);
            return new User(
                    userRows.getLong("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    Objects.requireNonNull(userRows.getDate("birthday")).toLocalDate()
            );
        }
        return null;
    }

    private User insertUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        Long returnedUserId = simpleJdbcInsert.executeAndReturnKey(this.userToMap(user)).longValue();
        if (!returnedUserId.equals(0L)) {
            user.setId(returnedUserId);
        }
        insertFriendships(user.getFriendships());
        return getById(returnedUserId);
    }

    private User updateUser(User user) {
        List<Friendship> friendships = user.getFriendships();
        jdbcTemplate.update(UPDATE_USER,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        jdbcTemplate.update(DELETE_ALL_FRIENDSHIPS_CORRESPONDING_USER, user.getId());
        if (!friendships.isEmpty()) {
            insertFriendships(friendships);
        }
        return getById(user.getId());
    }

    private void insertFriendships(List<Friendship> friendships) {
        if (friendships != null) {
            for (Friendship friendship : friendships) {
                SimpleJdbcInsert friendshipInsert = new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName("friendships")
                        .usingGeneratedKeyColumns("friendship_id");
                friendshipInsert.executeAndReturnKey(this.friendshipToMap(friendship)).longValue();
            }
        }
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getLong("user_id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("name"),
                Objects.requireNonNull(resultSet.getDate("birthday")).toLocalDate());
    }

    private Friendship mapFriendRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        Long friendship_id = resultSet.getLong("friendship_id");
        Long userId = resultSet.getLong("user_id");
        Long friendId = resultSet.getLong("friend_id");
        FriendshipStatus status = FriendshipStatus.valueOf(resultSet.getString("status"));
        User user = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_USER, this::mapRowToUser, userId);
        User friend = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_USER, this::mapRowToUser, friendId);
        return new Friendship(friendship_id, user, friend, status);
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

    private Like mapLikeRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        Long likeId = resultSet.getLong("like_id");
        Long userId = resultSet.getLong("user_id");
        Long filmId = resultSet.getLong("film_id");
        User user = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_USER, this::mapRowToUser, userId);
        Film film = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_FILM, this::mapRowToFilm, filmId);
        return new Like(likeId, film, user);
    }

    private Map<String, Object> userToMap(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());
        return values;
    }

    private Map<String, Object> friendshipToMap(Friendship friendship) {
        Map<String, Object> values = new HashMap<>();
        values.put("user_id", friendship.getUser().getId());
        values.put("friend_id", friendship.getFriend().getId());
        values.put("status", friendship.getStatus());
        return values;
    }
}
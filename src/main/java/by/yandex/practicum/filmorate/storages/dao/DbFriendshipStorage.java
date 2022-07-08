package by.yandex.practicum.filmorate.storages.dao;

import by.yandex.practicum.filmorate.exceptions.FriendshipNotFoundException;
import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.models.FriendshipStatus;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.storages.FriendshipStorage;
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
public class DbFriendshipStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL_FRIENDSHIPS = "SELECT * FROM friendships";
    private static final String SELECT_CORRESPONDING_FRIENDSHIP = "SELECT friendship_id, user_id, friend_id, status " +
            "FROM friendships WHERE friendship_id = ?";
    private static final String DELETE_CORRESPONDING_FRIENDSHIP = "DELETE FROM friendships WHERE friendship_id = ?";
    private static final String DELETE_ALL_FRIENDSHIPS = "DELETE FROM friendships";
    private static final String SELECT_CORRESPONDING_USER = "SELECT user_id, email, login, name, birthday " +
            "FROM users WHERE user_id = ?";
    private static final String UPDATE_CORRESPONDING_FRIENDSHIP = "UPDATE friendships SET user_id = ?, friend_id = ?" +
            ", status = ? WHERE friendship_id = ?";

    public DbFriendshipStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Friendship> getAll() {
        return jdbcTemplate.query(SELECT_ALL_FRIENDSHIPS, this::mapRowToFriendship);
    }

    @Override
    public Friendship getById(Long id) {
        if (id == null) {
            return null;
        }
        Friendship friendship;
        try {
            friendship = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_FRIENDSHIP, this::mapRowToFriendship, id);
        } catch (EmptyResultDataAccessException e) {
            friendship = null;
        }
        return friendship;
    }

    @Override
    public Friendship put(Friendship newFriendship) {
        Friendship friendship = getById(newFriendship.getId());
        if (friendship == null) {
            return insertFriendship(newFriendship);
        }
        return updateFriendship(newFriendship);
    }

    @Override
    public Friendship remove(Friendship friendship) {
        jdbcTemplate.update(DELETE_CORRESPONDING_FRIENDSHIP, friendship.getId());
        return friendship;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(DELETE_ALL_FRIENDSHIPS);
    }

    private Friendship insertFriendship(Friendship friendship) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("friendships")
                .usingGeneratedKeyColumns("friendship_id");
        Long returnedId = simpleJdbcInsert.executeAndReturnKey(this.friendshipToMap(friendship)).longValue();
        if (!returnedId.equals(0L)) {
            friendship.setId(returnedId);
        }
        return getById(returnedId);
    }

    private Friendship updateFriendship(Friendship friendship) {
        jdbcTemplate.update(UPDATE_CORRESPONDING_FRIENDSHIP,
                friendship.getUser().getId(),
                friendship.getFriend().getId(),
                String.valueOf(friendship.getStatus()),
                friendship.getId());
        return getById(friendship.getId());
    }

    private Friendship mapRowToFriendship(ResultSet resultSet, int rowNum) throws SQLException {
        Long friendshipId = resultSet.getLong("friendship_id");
        Long userId = resultSet.getLong("user_id");
        Long friendId = resultSet.getLong("friend_id");
        FriendshipStatus status = FriendshipStatus.valueOf(resultSet.getString("status"));
        User user = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_USER, this::mapRowToUser, userId);
        User friend = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_USER, this::mapRowToUser, friendId);
        return new Friendship(friendshipId, user, friend, status);
    }

    private Map<String, Object> friendshipToMap(Friendship friendship) {
        Map<String, Object> values = new HashMap<>();
        values.put("user_id", friendship.getUser().getId());
        values.put("friend_id", friendship.getFriend().getId());
        values.put("status", String.valueOf(friendship.getStatus()));
        return values;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getLong("user_id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("name"),
                Objects.requireNonNull(resultSet.getDate("birthday")).toLocalDate());
    }
}
package by.yandex.practicum.filmorate.storages.dao;

import by.yandex.practicum.filmorate.models.dictionaries.MpaRating;
import by.yandex.practicum.filmorate.storages.MpaRatingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class DbMpaRatingStorage implements MpaRatingStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL_RATINGS = "SELECT mpa_rating_id, code, description FROM mpa_ratings";
    private static final String SELECT_CORRESPONDING_RATING = "SELECT mpa_rating_id, code, description " +
            "FROM mpa_ratings WHERE mpa_rating_id = ?";
    private static final String UPDATE_CORRESPONDING_RATING = "UPDATE mpa_ratings SET code = ?, description = ?" +
            "WHERE mpa_rating_id = ?";
    private static final String DELETE_CORRESPONDING_RATING = "DELETE FROM mpa_ratings WHERE mpa_rating_id = ?";
    private static final String DELETE_ALL_RATINGS = "DELETE FROM mpa_ratings";

    @Autowired
    public DbMpaRatingStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MpaRating> getAll() {
        return jdbcTemplate.query(SELECT_ALL_RATINGS, this::mapRowToMPARating);
    }

    @Override
    public MpaRating getById(Long id) {
        if (id == null) {
            return null;
        }
        if (id.compareTo(0L) <= 0) {
            return null;
        }
        return jdbcTemplate.queryForObject(SELECT_CORRESPONDING_RATING, this::mapRowToMPARating, id);
    }

    @Override
    public MpaRating put(MpaRating newRating) {
        MpaRating ratingDictionary = getById(newRating.getId());
        if (ratingDictionary == null) {
            return insertRating(newRating);
        }
        return updateRating(newRating);
    }

    @Override
    public MpaRating remove(MpaRating rating) {
        jdbcTemplate.update(DELETE_CORRESPONDING_RATING, rating.getId());
        return rating;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(DELETE_ALL_RATINGS);
    }

    private MpaRating insertRating(MpaRating rating) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("mpa_ratings")
                .usingGeneratedKeyColumns("mpa_rating_id");
        Long returnedId = simpleJdbcInsert.executeAndReturnKey(this.ratingToMap(rating)).longValue();
        if (!returnedId.equals(0L)) {
            rating.setId(returnedId);
        }
        return getById(returnedId);
    }

    private MpaRating updateRating(MpaRating rating) {
        jdbcTemplate.update(UPDATE_CORRESPONDING_RATING,
                rating.getCode(),
                rating.getDescription(),
                rating.getId());
        return getById(rating.getId());
    }

    private Map<String, Object> ratingToMap(MpaRating rating) {
        Map<String, Object> values = new HashMap<>();
        values.put("code", rating.getCode());
        values.put("description", rating.getDescription());
        return values;
    }

    private MpaRating mapRowToMPARating(ResultSet resultSet, int rowNum) throws SQLException {
        Long ratingId = resultSet.getLong("mpa_rating_id");
        String code = resultSet.getString("code");
        String description = resultSet.getString("description");
        return new MpaRating(ratingId, code, description);
    }
}
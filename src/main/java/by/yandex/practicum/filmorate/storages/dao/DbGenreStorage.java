package by.yandex.practicum.filmorate.storages.dao;

import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.storages.GenreStorage;
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
public class DbGenreStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_ALL_GENRES = "SELECT genre_id, name, description FROM genres";
    private static final String SELECT_CORRESPONDING_GENRE = "SELECT genre_id, name, description FROM genres " +
            "WHERE genre_id = ?";
    private static final String UPDATE_CORRESPONDING_GENRE = "UPDATE genres SET name = ?, description = ? " +
            "WHERE genre_id = ?";
    private static final String DELETE_CORRESPONDING_GENRE = "DELETE FROM genres WHERE genre_id = ?";
    private static final String DELETE_ALL_GENRES = "DELETE FROM genres";

    @Autowired
    public DbGenreStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(SELECT_ALL_GENRES, this::mapRowToGenre);
    }

    @Override
    public Genre getById(Long id) {
        if (id == null) {
            return null;
        }
        if (id.compareTo(0L) <= 0) {
            return null;
        }
        return jdbcTemplate.queryForObject(SELECT_CORRESPONDING_GENRE, this::mapRowToGenre, id);
    }

    @Override
    public Genre put(Genre newGenre) {
        Genre genre = getById(newGenre.getId());
        if (genre == null) {
            return insertGenre(newGenre);
        }
        return updateGenre(newGenre);
    }

    @Override
    public Genre remove(Genre genre) {
        jdbcTemplate.update(DELETE_CORRESPONDING_GENRE, genre.getId());
        return genre;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(DELETE_ALL_GENRES);
    }

    private Genre insertGenre(Genre genre) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("genres")
                .usingGeneratedKeyColumns("genre_id");
        Long returnedId = simpleJdbcInsert.executeAndReturnKey(this.genreToMap(genre)).longValue();
        if (!returnedId.equals(0L)) {
            genre.setId(returnedId);
        }
        return getById(returnedId);
    }

    private Genre updateGenre(Genre genre) {
        jdbcTemplate.update(UPDATE_CORRESPONDING_GENRE,
                genre.getName(),
                genre.getDescription(),
                genre.getId());
        return getById(genre.getId());
    }

    private Map<String, Object> genreToMap(Genre genre) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", genre.getName());
        values.put("description", genre.getDescription());
        return values;
    }

    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Long genreId = resultSet.getLong("genre_id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        return new Genre(genreId, name, description);
    }
}
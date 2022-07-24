package by.yandex.practicum.filmorate.storages.dao;

import by.yandex.practicum.filmorate.exceptions.DirectorNotFoundException;
import by.yandex.practicum.filmorate.models.Director;
import by.yandex.practicum.filmorate.storages.DirectorStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class DbDirectorStorage implements DirectorStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String SELECT_DIRECTOR_BY_ID = "SELECT director_id, name FROM directors WHERE director_id = ?";
    private static final String SELECT_ALL_DIRECTORS = "SELECT director_id, name FROM directors ORDER BY director_id";
    private static final String ADD_DIRECTOR = "INSERT INTO directors (name) VALUES (?)";
    private static final String PUT_DIRECTOR = "UPDATE directors SET name = ? WHERE director_id = ?";
    private static final String DELETE_DIRECTOR = "DELETE FROM directors WHERE director_id = ?";
    private static final String DELETE_ALL_DIRECTORS = "DELETE FROM directors";

    @Autowired
    public DbDirectorStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Director> getAll() {
        return jdbcTemplate.query(SELECT_ALL_DIRECTORS, new RowMapper<Director>() {
            @Override
            public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Director(rs.getLong("director_id"), rs.getString("name"));
            }
        });
    }

    @Override
    public Director getById(Long id) {
        Director director;
        try {
            director = jdbcTemplate.queryForObject(SELECT_DIRECTOR_BY_ID, (rs, rowNum) ->
                    new Director(rs.getLong("director_id"), rs.getString("name")), id);
        } catch (EmptyResultDataAccessException e) {
            log.info("Директор ID {} не найден.", id);
            director = null;
        }
        return director;
    }

    @Override
    public Director add(Director director) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(ADD_DIRECTOR, new String[]{"DIRECTOR_ID"});
            stmt.setString(1, director.getName());
            return stmt;
        }, keyHolder);
        long directorId = keyHolder.getKey().intValue();
        director.setId(directorId);
        return director;
    }

    @Override
    public Director put(Director director) {
        if (jdbcTemplate.update(PUT_DIRECTOR, director.getName(), director.getId()) != 1) {
            log.info("Режиссёр с идентификатором {} не найден.", director.getId());
            throw new DirectorNotFoundException(String.format("Режиссёр ID %d не найден.", director.getId()));
        }
        return director;
    }

    @Override
    public Director remove(Director director) {
        jdbcTemplate.update(DELETE_DIRECTOR, director.getId());
        return director;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(DELETE_ALL_DIRECTORS);
    }
}

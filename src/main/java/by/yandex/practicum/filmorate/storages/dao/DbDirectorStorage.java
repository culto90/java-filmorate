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

    @Autowired
    public DbDirectorStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Director> getAll() {
        String sqlQuery = "SELECT * FROM directors ORDER BY DIRECTOR_ID";
        return jdbcTemplate.query(sqlQuery, new RowMapper<Director>() {
            @Override
            public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Director(rs.getLong("director_id"), rs.getString("name"));
            }
        });
    }

    @Override
    public Director getById(Long id) {
        final String sqlQuery = "SELECT * FROM directors WHERE DIRECTOR_ID = ?";
        Director director;
        try {
            director = jdbcTemplate.queryForObject(sqlQuery, new RowMapper<Director>() {
                @Override
                public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Director(rs.getLong("director_id"), rs.getString("name"));
                }
            }, id);
        } catch (EmptyResultDataAccessException e) {
            log.info("Директор ID {} не найден.", id);
            throw new DirectorNotFoundException(String.format("Режиссёр ID %d не найден.", id));
        }
        return director;
    }

    @Override
    public Director add(Director director) {
        String sqlQuery = "INSERT INTO directors (NAME) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"DIRECTOR_ID"});
                stmt.setString(1, director.getName());
                return stmt;
            }
        }, keyHolder);
        long directorId = keyHolder.getKey().intValue();
        director.setId(directorId);
        return director;
    }

    @Override
    public Director put(Director director) {
        String sqlQuery = "UPDATE directors SET NAME = ? WHERE DIRECTOR_ID = ?";
        if (jdbcTemplate.update(sqlQuery, director.getName(), director.getId()) != 1) {
            log.info("Режиссёр с идентификатором {} не найден.", director.getId());
            throw new DirectorNotFoundException(String.format("Режиссёр ID %d не найден.", director.getId()));
        }
        return director;
    }

    @Override
    public Director remove(Director director) {
        String sqlQuery = "DELETE FROM directors WHERE DIRECTOR_ID = ?";
        jdbcTemplate.update(sqlQuery, director.getId());
        return director;
    }

    @Override
    public void removeAll() {
    }
}

package by.yandex.practicum.filmorate.storages.dao;

import by.yandex.practicum.filmorate.exceptions.FilmorateRepositoryException;
import by.yandex.practicum.filmorate.exceptions.GenreNotFoundException;
import by.yandex.practicum.filmorate.models.*;
import by.yandex.practicum.filmorate.models.MpaRating;
import by.yandex.practicum.filmorate.storages.FilmStorage;
import by.yandex.practicum.filmorate.storages.dao.cache.GenreCachedDictionary;
import by.yandex.practicum.filmorate.storages.dao.cache.MpaRatingCachedDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Primary
@Repository
public class DbFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaRatingCachedDictionary mpaRatingCachedDictionary;
    private final GenreCachedDictionary genreCachedDictionary;
    private static final String SELECT_ALL_FILMS = "SELECT film_id, name, description, release_date, duration, " +
            "rate, mpa_rating_id FROM films";
    private static final String SELECT_CORRESPONDING_MPA_RATING = "SELECT mpa_rating_id, code, description " +
            "FROM mpa_ratings WHERE mpa_rating_id = ?";
    private static final String SELECT_CORRESPONDING_FILM = "SELECT film_id, name, description, release_date, " +
            "duration, rate, mpa_rating_id FROM films WHERE film_id = ?";
    private static final String UPDATE_FILM = "UPDATE films SET name = ?, description = ?, release_date = ?, " +
            "duration = ?, rate = ?, mpa_rating_id = ? WHERE film_id = ?";
    private static final String DELETE_CORRESPONDING_FILM = "DELETE FROM likes WHERE film_id = ?;" +
            "DELETE FROM films WHERE film_id = ?";
    private static final String DELETE_ALL_FILMS = "DELETE FROM likes;" +
            "DELETE FROM films";
    private static final String SELECT_ALL_LIKES_CORRESPONDING_FILM = "SELECT like_id, film_id, user_id FROM likes " +
            "WHERE film_id = ?";
    private static final String SELECT_CORRESPONDING_USER = "SELECT user_id, email, login, name, birthday " +
            "FROM users WHERE user_id = ?";
    private static final String SELECT_ALL_GENRES_CORRESPONDING_FILM = "SELECT g.genre_id, name, description " +
            "FROM genres g, film_genres fg WHERE g.genre_id = fg.genre_id AND fg.film_id = ?";
    private static final String DELETE_ALL_GENRES_CORRESPONDING_FILM = "DELETE FROM film_genres WHERE film_id = ?";
    private static final String SELECT_DIRECTOR_FROM_FILM = "SELECT film_directors.director_id, directors.name " +
            "FROM film_directors LEFT OUTER JOIN directors ON film_directors.director_id = directors.director_id " +
            "WHERE film_id = ?";
    private static final String SELECT_FILMS_ID_BY_DIRECTOR = "SELECT films.film_id FROM film_directors " +
            "LEFT OUTER JOIN films ON films.film_id = film_directors.film_id " +
            "WHERE film_directors.director_id = ?";
    private static final String DELETE_FROM_FILM_DIRECTORS_BY_FILM_ID = "DELETE FROM film_directors WHERE FILM_ID = ?";
    private static final String MERGE_FILM_DIRECTORS = "MERGE INTO film_directors (film_id, director_id) VALUES (?,?)";

    @Autowired
    public DbFilmStorage(JdbcTemplate jdbcTemplate,
                         MpaRatingCachedDictionary mpaRatingCachedDictionary,
                         GenreCachedDictionary genreCachedDictionary) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaRatingCachedDictionary = mpaRatingCachedDictionary;
        this.genreCachedDictionary = genreCachedDictionary;
    }

    @Override
    public List<Film> getAll() {
        List<Film> films = jdbcTemplate.query(SELECT_ALL_FILMS, this::mapRowToFilm);
        for (Film film : films) {
            List<Like> likes = jdbcTemplate.query(SELECT_ALL_LIKES_CORRESPONDING_FILM,
                    this::mapLikeRowToFilm,
                    film.getId());
            film.setLikeList(likes);
            List<Genre> genres = jdbcTemplate.query(SELECT_ALL_GENRES_CORRESPONDING_FILM,
                    this::mapGenreRowToFilm,
                    film.getId());
            film.setGenreList(genres);
            film.setDirectorList(applyDirectors(film.getId()));
        }
        return films;
    }

    @Override
    public Film getById(Long id) {
        if (id == null) {
            return null;
        }
        Film film;
        try {
            film = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_FILM, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e) {
            film = null;
        }
        if (film != null) {
            List<Like> likes = jdbcTemplate.query(SELECT_ALL_LIKES_CORRESPONDING_FILM,
                    this::mapLikeRowToFilm,
                    film.getId());
            film.setLikeList(likes);
            List<Genre> genres = jdbcTemplate.query(SELECT_ALL_GENRES_CORRESPONDING_FILM,
                    this::mapGenreRowToFilm,
                    film.getId());
            film.setGenreList(genres);
            film.setDirectorList(applyDirectors(film.getId()));
        }
        return film;
    }

    private List<Director> applyDirectors (long filmId) {
        return jdbcTemplate.query(SELECT_DIRECTOR_FROM_FILM, (rs, rowNum)
                -> new Director(rs.getLong("director_id"), rs.getString("name")), filmId);
    }

    @Override
    public Film put(Film newFilm) {
        this.validateMpaRating(newFilm);
        this.validateGenre(newFilm);
        Film film = getById(newFilm.getId());
        if (film == null) {
            return insertFilm(newFilm);
        }
        return updateFilm(newFilm);
    }

    @Override
    public Film remove(Film film) {
        jdbcTemplate.update(DELETE_CORRESPONDING_FILM, film.getId(), film.getId());
        return film;
    }

    @Override
    public void removeAll() {
        jdbcTemplate.update(DELETE_ALL_FILMS);
    }

    public List<Film> getFilmsByDirectorId(long directorId) {
        List<Long> film_id = jdbcTemplate.query(SELECT_FILMS_ID_BY_DIRECTOR, (rs, rowNum)
                -> rs.getLong("film_id"), directorId);
        List<Film> films = new ArrayList<>();
        for (Long id : film_id) {
            films.add(getById(id));
        }
        return films; // Получаем список всех фильмов режиссёра для последующей сортировки
    }

    private Film insertFilm(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        Long returnedId = simpleJdbcInsert.executeAndReturnKey(this.filmToMap(film)).longValue();
        insertGenres(film.getGenres(), returnedId);
        insertDirectors(film.getDirectors(), returnedId);
        return this.getById(returnedId);
    }

    private Film updateFilm(Film film) {
        MpaRating rating = film.getRating();
        List<Genre> genres = film.getGenres();
        List<Director> directors = film.getDirectors();
        Long ratingId = null;
        if (rating != null) {
            ratingId = rating.getId();
        }
        jdbcTemplate.update(UPDATE_FILM,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                ratingId,
                film.getId()
        );
        jdbcTemplate.update(DELETE_ALL_GENRES_CORRESPONDING_FILM, film.getId());
        if (!genres.isEmpty()) {
            insertGenres(genres, film.getId());
        }
        insertDirectors(directors, film.getId());
        Film updatedFilm = getById(film.getId());
        if (directors == null) {                // При получении null в поле directors в обновлённом фильме.
            updatedFilm.setDirectorList(null);  // Иначе - List с нулевым размером (не проходит тесты).
        }
        return updatedFilm;
    }

    private void insertGenres(List<Genre> genres, Long filmId) {
        if (genres != null) {
            for (Genre genre : genres) {
                SimpleJdbcInsert genresInsert = new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName("film_genres")
                        .usingGeneratedKeyColumns("film_genre_id");
                genresInsert.executeAndReturnKey(this.genreToMap(genre, filmId)).longValue();
            }
        }
    }

    private void insertDirectors(List<Director> directors, long filmId) {
        jdbcTemplate.update(DELETE_FROM_FILM_DIRECTORS_BY_FILM_ID, filmId);
        if (directors != null) {
            for (Director director : directors) {
                jdbcTemplate.update(MERGE_FILM_DIRECTORS, filmId, director.getId());
            }
        }
    }

    private Map<String, Object> filmToMap(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("rate", film.getRate());
        MpaRating rating = film.getRating();
        if (rating != null) {
            values.put("mpa_rating_id", rating.getId());
        }
        return values;
    }

    private Map<String, Object> genreToMap(Genre genre, Long filmId) {
        Map<String, Object> values = new HashMap<>();
        values.put("genre_id", genre.getId());
        values.put("film_id", filmId);
        return values;
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

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getLong("user_id"),
                resultSet.getString("email"),
                resultSet.getString("login"),
                resultSet.getString("name"),
                Objects.requireNonNull(resultSet.getDate("birthday")).toLocalDate());
    }

    private Like mapLikeRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Long likeId = resultSet.getLong("like_id");
        Long userId = resultSet.getLong("user_id");
        Long filmId = resultSet.getLong("film_id");
        User user = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_USER, this::mapRowToUser, userId);
        Film film = jdbcTemplate.queryForObject(SELECT_CORRESPONDING_FILM, this::mapRowToFilm, filmId);
        return new Like(likeId, film, user);
    }

    private Genre mapGenreRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Long genreId = resultSet.getLong("genre_id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        return new Genre(genreId, name, description);
    }

    private void validateGenre(Film film) {
        List<Genre> genres = film.getGenres();
        for (Genre genre : genres) {
            if (!genreCachedDictionary.isExists(genre)) {
                throw new GenreNotFoundException("Genre id = '" + genre.getId() + "' not found in database.");
            }
        }
    }

    private void validateMpaRating(Film film) {
        MpaRating rating = (MpaRating) film.getRating();
        if (rating == null) {
            return;
        }

        if (!mpaRatingCachedDictionary.isExists(rating)) {
            throw new FilmorateRepositoryException("Mpa rating id = '" + rating.getId() + "' not found in database.");
        }
    }
}
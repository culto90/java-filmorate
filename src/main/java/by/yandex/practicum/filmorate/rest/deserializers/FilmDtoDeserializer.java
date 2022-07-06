package by.yandex.practicum.filmorate.rest.deserializers;

import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.models.dictionaries.MpaRating;
import by.yandex.practicum.filmorate.rest.dto.FilmDto;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class FilmDtoDeserializer extends StdDeserializer<FilmDto> {

    public FilmDtoDeserializer() {
        this(null);
    }

    public FilmDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public FilmDto deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode filmBody = jp.getCodec().readTree(jp);
        FilmDto filmDto = new FilmDto();

        if (filmBody.get("id") != null) {
            filmDto.setId(filmBody.get("id").asLong());
        }
        filmDto.setName(filmBody.get("name").asText());
        filmDto.setDescription(filmBody.get("description").asText());
        filmDto.setReleaseDate(LocalDate.parse(filmBody.get("releaseDate").asText()));
        filmDto.setDuration(filmBody.get("duration").asInt());
        JsonNode filmRate = filmBody.get("rate");
        if (filmRate != null) {
            filmDto.setRate(filmRate.asDouble());
        }

        JsonNode mpaRating = filmBody.get("mpa");
        if (mpaRating != null) {
            MpaRating rating = new MpaRating();
            JsonNode filmRatingId = mpaRating.get("id");
            JsonNode filmRatingCode = mpaRating.get("code");
            JsonNode filmRatingName = mpaRating.get("name");
            if (filmRatingId != null) {
                rating.setId(filmRatingId.asLong());
            }
            if (filmRatingCode != null) {
                rating.setCode(filmRatingCode.asText());
            }
            if (filmRatingName != null) {
                rating.setDescription(filmRatingName.asText());
            }
            filmDto.setRating(rating);
        }

        ArrayNode filmGenres = (ArrayNode) filmBody.get("genres");
        if (filmGenres != null) {
            List<Genre> genres = new ArrayList<>();
            for (int i = 0; i < filmGenres.size(); i++) {
                Genre genre = new Genre();
                JsonNode genreId = filmGenres.get(i).get("id");
                JsonNode genreName = filmGenres.get(i).get("name");
                JsonNode genreDesc = filmGenres.get(i).get("description");
                if (genreId != null) {
                    genre.setId(genreId.asLong());
                }
                if (genreName != null) {
                    genre.setName(genreName.asText());
                }
                if (genreDesc != null) {
                    genre.setDescription(genreDesc.asText());
                }
                genres.add(genre);
            }
            filmDto.setGenres(genres);
        }
        return filmDto;
    }
}

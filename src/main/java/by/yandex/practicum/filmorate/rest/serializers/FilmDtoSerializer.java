package by.yandex.practicum.filmorate.rest.serializers;

import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.models.dictionaries.Dictionary;
import by.yandex.practicum.filmorate.rest.dto.FilmDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilmDtoSerializer extends StdSerializer<FilmDto> {

    protected FilmDtoSerializer() {
        this(null);
    }

    protected FilmDtoSerializer(Class<FilmDto> t) {
        super(t);
    }

    @Override
    public void serialize(FilmDto filmDto, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        List<Genre> genres = filmDto.getGenres();
        List<Like> likes = filmDto.getLikes();
        Dictionary mpa = filmDto.getRating();

        jgen.writeStartObject();
        jgen.writeNumberField("id", filmDto.getId());
        jgen.writeStringField("name", filmDto.getName());
        jgen.writeStringField("description", filmDto.getDescription());
        jgen.writeStringField("releaseDate", filmDto.getReleaseDate().format(DateTimeFormatter.ISO_DATE));
        jgen.writeNumberField("duration", filmDto.getDuration());
        jgen.writeNumberField("rate", filmDto.getRate());
        if (mpa != null) {
            jgen.writeFieldName("mpa");
            jgen.writeStartObject();
            jgen.writeNumberField("id", mpa.getId());
            jgen.writeStringField("code", mpa.getDescription());
            jgen.writeStringField("name", mpa.getCode());
            jgen.writeEndObject();
        }
        jgen.writeFieldName("genres");
        jgen.writeStartArray();
        for (Genre genre : genres) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", genre.getId());
            jgen.writeStringField("name", genre.getName());
            jgen.writeStringField("description", genre.getDescription());
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeFieldName("likes");
        jgen.writeStartArray();
        for (Like like : likes) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", like.getId());
            jgen.writeFieldName("user");
            jgen.writeStartObject();
            jgen.writeNumberField("id", like.getUser().getId());
            jgen.writeStringField("email", like.getUser().getEmail());
            jgen.writeStringField("login", like.getUser().getLogin());
            jgen.writeStringField("name", like.getUser().getName());
            jgen.writeStringField("birthday", like.getUser().getBirthday().format(DateTimeFormatter.ISO_DATE));
            jgen.writeEndObject();
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}

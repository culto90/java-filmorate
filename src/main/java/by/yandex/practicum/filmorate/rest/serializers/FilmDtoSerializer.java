package by.yandex.practicum.filmorate.rest.serializers;

import by.yandex.practicum.filmorate.models.Like;
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
        List<Like> likes = filmDto.getLikes();

        jgen.writeStartObject();
        jgen.writeNumberField("id", filmDto.getId());
        jgen.writeStringField("name", filmDto.getName());
        jgen.writeStringField("description", filmDto.getDescription());
        jgen.writeStringField("releaseDate", filmDto.getReleaseDate().format(DateTimeFormatter.ISO_DATE));
        jgen.writeNumberField("duration", filmDto.getDuration());
        jgen.writeNumberField("rate", filmDto.getRate());
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

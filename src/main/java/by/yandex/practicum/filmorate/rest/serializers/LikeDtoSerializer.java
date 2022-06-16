package by.yandex.practicum.filmorate.rest.serializers;

import by.yandex.practicum.filmorate.rest.dto.LikeDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LikeDtoSerializer  extends StdSerializer<LikeDto> {

    public LikeDtoSerializer() {
        this(null);
    }

    protected LikeDtoSerializer(Class<LikeDto> t) {
        super(t);
    }

    @Override
    public void serialize(LikeDto likeDto, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", likeDto.getId());
        jgen.writeFieldName("film");
        jgen.writeStartObject();
        jgen.writeNumberField("id", likeDto.getFilm().getId());
        jgen.writeStringField("name", likeDto.getFilm().getName());
        jgen.writeStringField("description", likeDto.getFilm().getDescription());
        jgen.writeStringField("releaseDate", likeDto.getFilm()
                .getReleaseDate().format(DateTimeFormatter.ISO_DATE));
        jgen.writeNumberField("duration", likeDto.getFilm().getDuration());
        jgen.writeNumberField("rate", likeDto.getFilm().getRate());
        jgen.writeEndObject();
        jgen.writeFieldName("user");
        jgen.writeStartObject();
        jgen.writeNumberField("id", likeDto.getUser().getId());
        jgen.writeStringField("email", likeDto.getUser().getEmail());
        jgen.writeStringField("login", likeDto.getUser().getLogin());
        jgen.writeStringField("name", likeDto.getUser().getName());
        jgen.writeStringField("birthday", likeDto.getUser()
                .getBirthday().format(DateTimeFormatter.ISO_DATE));
        jgen.writeEndObject();
        jgen.writeEndObject();
    }
}

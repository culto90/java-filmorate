package by.yandex.practicum.filmorate.rest.serializers;

import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.rest.dto.UserDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserDtoSerializer extends StdSerializer<UserDto> {

    public UserDtoSerializer() {
        this(null);
    }

    protected UserDtoSerializer(Class<UserDto> t) {
        super(t);
    }

    @Override
    public void serialize(UserDto userDto, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        List<Friendship> friendships = userDto.getFriendships();
        List<Like> likes = userDto.getLikes();
        jgen.writeStartObject();
        jgen.writeNumberField("id", userDto.getId());
        jgen.writeStringField("email", userDto.getEmail());
        jgen.writeStringField("login", userDto.getLogin());
        jgen.writeStringField("name", userDto.getName());
        jgen.writeStringField("birthday", userDto.getBirthday().format(DateTimeFormatter.ISO_DATE));
        jgen.writeFieldName("friendships");
        jgen.writeStartArray();
        for (Friendship friendship : friendships) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", friendship.getId());
            jgen.writeFieldName("user");
            jgen.writeStartObject();
            jgen.writeNumberField("id", friendship.getFriend().getId());
            jgen.writeStringField("email", friendship.getFriend().getEmail());
            jgen.writeStringField("login", friendship.getFriend().getLogin());
            jgen.writeStringField("name", friendship.getFriend().getName());
            jgen.writeStringField("birthday", friendship.getFriend().getBirthday().format(DateTimeFormatter.ISO_DATE));
            jgen.writeEndObject();
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeFieldName("likes");
        jgen.writeStartArray();
        for (Like like : likes) {
            jgen.writeStartObject();
            jgen.writeNumberField("id", like.getId());
            jgen.writeFieldName("film");
            jgen.writeStartObject();
            jgen.writeNumberField("id", like.getFilm().getId());
            jgen.writeStringField("name", like.getFilm().getName());
            jgen.writeStringField("description", like.getFilm().getDescription());
            jgen.writeStringField("releaseDate", like.getFilm()
                    .getReleaseDate().format(DateTimeFormatter.ISO_DATE));
            jgen.writeNumberField("duration", like.getFilm().getDuration());
            jgen.writeNumberField("rate", like.getFilm().getRate());
            jgen.writeEndObject();
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeEndObject();
    }
}

package by.yandex.practicum.filmorate.rest.serializers;

import by.yandex.practicum.filmorate.models.Friendship;
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
        jgen.writeEndObject();
    }
}

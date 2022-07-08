package by.yandex.practicum.filmorate.rest.serializers;

import by.yandex.practicum.filmorate.rest.dto.FriendshipDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class FriendshipDtoSerializer extends StdSerializer<FriendshipDto> {

    public FriendshipDtoSerializer() {
        this(null);
    }

    protected FriendshipDtoSerializer(Class<FriendshipDto> t) {
        super(t);
    }

    @Override
    public void serialize(FriendshipDto friendshipDto, JsonGenerator jgen,
                          SerializerProvider serializerProvider) throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", friendshipDto.getId());
        jgen.writeStringField("status", String.valueOf(friendshipDto.getStatus()));
        jgen.writeFieldName("user");
        jgen.writeStartObject();
        jgen.writeNumberField("id", friendshipDto.getUser().getId());
        jgen.writeStringField("email", friendshipDto.getUser().getEmail());
        jgen.writeStringField("login", friendshipDto.getUser().getLogin());
        jgen.writeStringField("name", friendshipDto.getUser().getName());
        jgen.writeStringField("birthday", friendshipDto.getUser()
                .getBirthday().format(DateTimeFormatter.ISO_DATE));
        jgen.writeEndObject();
        jgen.writeFieldName("friend");
        jgen.writeStartObject();
        jgen.writeNumberField("id", friendshipDto.getFriend().getId());
        jgen.writeStringField("email", friendshipDto.getFriend().getEmail());
        jgen.writeStringField("login", friendshipDto.getFriend().getLogin());
        jgen.writeStringField("name", friendshipDto.getFriend().getName());
        jgen.writeStringField("birthday", friendshipDto.getFriend()
                .getBirthday().format(DateTimeFormatter.ISO_DATE));
        jgen.writeEndObject();
        jgen.writeEndObject();
    }
}

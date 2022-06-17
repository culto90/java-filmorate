package by.yandex.practicum.filmorate;

import by.yandex.practicum.filmorate.models.Film;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.converters.LikeToLikeDtoConverter;
import by.yandex.practicum.filmorate.rest.dto.LikeDto;
import by.yandex.practicum.filmorate.rest.serializers.LikeDtoSerializer;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LikeDtoSerializerTest {

    @Test
    public void serialize() throws IOException {
        String expected = "{\"id\":3,\"film\":{\"id\":2,\"name\":\"name\",\"description\":\"desc\",\"releaseDate\":\"2000-01-01\",\"duration\":100,\"rate\":4.0},\"user\":{\"id\":1,\"email\":\"email@example.com\",\"login\":\"loginName\",\"name\":\"userName\",\"birthday\":\"2022-06-16\"}}";

        User user = new User();
        user.setId(1L);
        user.setEmail("email@example.com");
        user.setLogin("loginName");
        user.setName("userName");
        user.setBirthday(LocalDate.now());

        Film film = new Film();
        film.setId(2L);
        film.setName("name");
        film.setDescription("desc");
        film.setRate(4);
        film.setDuration(100);
        film.setReleaseDate(LocalDate.of(2000,1, 1));

        Like like = new Like(3L, film, user);

        LikeDto likeDto = new LikeToLikeDtoConverter().convert(like);

        Writer jsonWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
        SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();
        new LikeDtoSerializer().serialize(likeDto, jsonGenerator, serializerProvider);
        jsonGenerator.flush();
        System.out.println(jsonWriter);
        assertEquals(expected, jsonWriter.toString());
    }
}

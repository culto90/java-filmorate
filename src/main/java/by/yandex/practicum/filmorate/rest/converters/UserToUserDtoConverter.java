package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.exceptions.DtoConverterException;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.dto.UserDto;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @SneakyThrows
    @Override
    public UserDto convert(User user) {
        if (user == null) {
            throw new DtoConverterException("Cannot convert input object.");
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setLogin(user.getLogin());
        userDto.setName(user.getName());
        userDto.setBirthday(user.getBirthday());
        userDto.setFriendships(user.getFriendships());
        userDto.setLikes(user.getLikes());
        return userDto;
    }

}

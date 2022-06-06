package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.exceptions.DtoConverterException;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.dto.UserDto;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {
    @SneakyThrows
    @Override
    public User convert(UserDto dto) {
        if (dto == null) {
            throw new DtoConverterException("Cannot convert output object.");
        }
        User user =  new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setBirthday(dto.getBirthday());
        return user;
    }
}

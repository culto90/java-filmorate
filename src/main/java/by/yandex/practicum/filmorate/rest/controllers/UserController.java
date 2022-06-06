package by.yandex.practicum.filmorate.rest.controllers;

import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.converters.UserDtoToUserConverter;
import by.yandex.practicum.filmorate.rest.dto.UserDto;
import by.yandex.practicum.filmorate.rest.converters.UserToUserDtoConverter;
import by.yandex.practicum.filmorate.services.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
public class UserController {
    private final UserService userService;
    private final UserToUserDtoConverter toUserDtoConverter;
    private final UserDtoToUserConverter dtoToUserConverter;

    public UserController(UserService userService, UserToUserDtoConverter userMapper, UserToUserDtoConverter toUserDtoConverter, UserDtoToUserConverter dtoToUserConverter) {
        this.userService = userService;
        this.toUserDtoConverter = toUserDtoConverter;
        this.dtoToUserConverter = dtoToUserConverter;
    }

    @GetMapping("/users")
    public List<UserDto> all() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(toUserDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDto one(@PathVariable Long id) {
        return toUserDtoConverter.convert(userService.findUserById(id));
    }

    @PostMapping("/users")
    public UserDto addUser(@Valid @RequestBody UserDto newUserDto) throws UserServiceException {
        User user = dtoToUserConverter.convert(newUserDto);
        UserDto createdUserDto = toUserDtoConverter.convert(userService.addUser(user));
        return createdUserDto;
    }

    @PutMapping("/users")
    public UserDto updateUser(@Valid @RequestBody UserDto newUserDto) throws UserServiceException {
        User user = dtoToUserConverter.convert(newUserDto);
        UserDto updatedUserDto = toUserDtoConverter.convert(userService.updateUser(user));
        return updatedUserDto;
    }

}

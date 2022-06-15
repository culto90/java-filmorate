package by.yandex.practicum.filmorate.rest.controllers;

import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.User;
import by.yandex.practicum.filmorate.rest.converters.FriendshipToFriendshipDtoConverter;
import by.yandex.practicum.filmorate.rest.converters.UserDtoToUserConverter;
import by.yandex.practicum.filmorate.rest.dto.FriendshipDto;
import by.yandex.practicum.filmorate.rest.dto.UserDto;
import by.yandex.practicum.filmorate.rest.converters.UserToUserDtoConverter;
import by.yandex.practicum.filmorate.services.FriendshipService;
import by.yandex.practicum.filmorate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
public class UserController {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final UserToUserDtoConverter toUserDtoConverter;
    private final UserDtoToUserConverter dtoToUserConverter;
    private final FriendshipToFriendshipDtoConverter friendshipConverter;

    @Autowired
    public UserController(UserService userService,
                          FriendshipService friendshipService,
                          UserToUserDtoConverter userMapper,
                          UserToUserDtoConverter toUserDtoConverter,
                          UserDtoToUserConverter dtoToUserConverter,
                          FriendshipToFriendshipDtoConverter friendshipConverter) {
        this.userService = userService;
        this.friendshipService = friendshipService;
        this.toUserDtoConverter = toUserDtoConverter;
        this.dtoToUserConverter = dtoToUserConverter;
        this.friendshipConverter = friendshipConverter;
    }

    @GetMapping("/users")
    public List<UserDto> getAll() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(toUserDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDto getOne(@PathVariable Long id) throws UserServiceException {
        return toUserDtoConverter.convert(userService.getUserById(id));
    }

    @PostMapping("/users")
    public UserDto addUser(@Valid @RequestBody UserDto newUserDto) throws UserServiceException {
        User user = dtoToUserConverter.convert(newUserDto);
        return toUserDtoConverter.convert(userService.addUser(user));
    }

    @PutMapping("/users")
    public UserDto updateUser(@Valid @RequestBody UserDto newUserDto) throws UserServiceException {
        User user = dtoToUserConverter.convert(newUserDto);
        return toUserDtoConverter.convert(userService.updateUser(user));
    }

    @GetMapping("/users/{id}/friends")
    public List<FriendshipDto> getAllFriends(@PathVariable long id) {
        return friendshipService.getFriendList(id).stream()
                        .map(friendshipConverter::convert)
                        .collect(Collectors.toList());
    }

    @GetMapping()
    public List<FriendshipDto> getSharedFriends(@PathVariable long id, @PathVariable long otherId) {
        return friendshipService.getSharedFriends(id, otherId).stream()
                .map(friendshipConverter::convert)
                .collect(Collectors.toList());
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public FriendshipDto addFriend(@PathVariable long id, @PathVariable long friendId) {
        return friendshipConverter.convert(friendshipService.addToFriend(id, friendId));
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        friendshipService.removeFriend(id, friendId);
    }


}

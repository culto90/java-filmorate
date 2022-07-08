package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.exceptions.DtoConverterException;
import by.yandex.practicum.filmorate.models.Friendship;
import by.yandex.practicum.filmorate.rest.dto.FriendshipDto;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FriendshipToFriendshipDtoConverter implements Converter<Friendship, FriendshipDto> {
    @SneakyThrows
    @Override
    public FriendshipDto convert(Friendship friendship) {
        if (friendship == null) {
            throw new DtoConverterException("Cannot convert input object. Class: " + this.getClass().getName());
        }
        FriendshipDto friendshipDto = new FriendshipDto();
        friendshipDto.setId(friendship.getId());
        friendshipDto.setStatus(friendship.getStatus());
        friendshipDto.setUser(friendship.getUser());
        friendshipDto.setFriend(friendship.getFriend());
        return friendshipDto;
    }


}

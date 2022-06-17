package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.rest.dto.LikeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LikeToLikeDtoConverter implements Converter<Like, LikeDto> {

    @Override
    public LikeDto convert(Like like) {
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setFilm(like.getFilm());
        likeDto.setUser(like.getUser());
        return likeDto;
    }
}

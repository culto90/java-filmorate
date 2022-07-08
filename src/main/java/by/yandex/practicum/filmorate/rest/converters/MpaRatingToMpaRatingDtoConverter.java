package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.exceptions.DtoConverterException;
import by.yandex.practicum.filmorate.models.dictionaries.MpaRating;
import by.yandex.practicum.filmorate.rest.dto.MpaRatingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MpaRatingToMpaRatingDtoConverter implements Converter<MpaRating, MpaRatingDto> {
    @Override
    public MpaRatingDto convert(MpaRating rating) {
        if (rating == null) {
            throw new DtoConverterException("Cannot convert input object.");
        }
        MpaRatingDto ratingDto = new MpaRatingDto();
        ratingDto.setId(rating.getId());
        ratingDto.setName(rating.getCode());
        ratingDto.setDescription(rating.getDescription());
        return ratingDto;
    }
}

package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.models.ErrorInfo;
import by.yandex.practicum.filmorate.rest.dto.ErrorInfoDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ErrorInfoToErrorInfoDtoConverter implements Converter<ErrorInfo, ErrorInfoDto> {

    @Override
    public ErrorInfoDto convert(ErrorInfo errorInfo) {
        return new ErrorInfoDto(errorInfo.getUrl(), errorInfo.getException());
    }
}

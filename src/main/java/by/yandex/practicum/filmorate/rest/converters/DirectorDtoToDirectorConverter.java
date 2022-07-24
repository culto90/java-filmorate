package by.yandex.practicum.filmorate.rest.converters;

import by.yandex.practicum.filmorate.exceptions.DtoConverterException;
import by.yandex.practicum.filmorate.models.Director;
import by.yandex.practicum.filmorate.rest.dto.DirectorDto;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DirectorDtoToDirectorConverter implements Converter<DirectorDto, Director> {

    @SneakyThrows
    @Override
    public Director convert(DirectorDto directorDto) {
        if (directorDto == null) {
            throw new DtoConverterException("Cannot convert output object.");
        }
        Director director = new Director();
        director.setId(directorDto.getId());
        director.setName(directorDto.getName());
        return director;
    }
}

package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.models.dictionaries.Dictionary;
import by.yandex.practicum.filmorate.rest.deserializers.FilmDtoDeserializer;
import by.yandex.practicum.filmorate.rest.serializers.FilmDtoSerializer;
import by.yandex.practicum.filmorate.rest.validators.constraints.ReleaseDateConstraint;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonDeserialize(using = FilmDtoDeserializer.class)
@JsonSerialize(using = FilmDtoSerializer.class)
public class FilmDto {
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    private long id;
    @NotBlank(message = "Name cannot be empty.")
    private String name;
    @Size(max = 200, message = "Description cannot be greater than 200 symbols.")
    private String description;
    @ReleaseDateConstraint
    private LocalDate releaseDate;
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    @Positive(message = "Duration must be positive.")
    private long duration;
    @Digits(integer = Integer.MAX_VALUE, fraction = 1)
    private double rate;
    private Dictionary rating;
    private List<Genre> genres;
    private List<Like> likes;
}
package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.Director;
import by.yandex.practicum.filmorate.models.Genre;
import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.models.MpaRating;
import by.yandex.practicum.filmorate.rest.validators.constraints.ReleaseDateConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @NotNull(message = "MPA Rating cannot be null.")
    private MpaRating mpa;
    private List<Genre> genres;
    private List<Like> likes;
    private List<Director> directors;
}
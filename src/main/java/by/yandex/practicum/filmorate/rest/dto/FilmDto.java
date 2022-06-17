package by.yandex.practicum.filmorate.rest.dto;

import by.yandex.practicum.filmorate.models.Like;
import by.yandex.practicum.filmorate.rest.serializers.FilmDtoSerializer;
import by.yandex.practicum.filmorate.rest.validators.constraints.ReleaseDateConstraint;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<Like> likes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setLikes(List<Like> likes) {
        this.likes = new ArrayList<>(likes);
    }

    public List<Like> getLikes() {
        return likes;
    }
}

package by.yandex.practicum.filmorate.models;

import lombok.ToString;

import java.time.LocalDate;

@ToString
public class Film {

    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    private double rate;

    public Film(Long id, String name, String description, LocalDate releaseDate, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(Long id, String name, String description, LocalDate releaseDate, long duration, double rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
    }


    public Film() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public long getDuration() {
        return duration;
    }

    public double getRate() {
        return this.rate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

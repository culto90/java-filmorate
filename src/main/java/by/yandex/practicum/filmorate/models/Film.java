package by.yandex.practicum.filmorate.models;

import by.yandex.practicum.filmorate.models.dictionaries.Dictionary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    private double rate;
    private Dictionary rating;
    private List<Genre> genres;
    private List<Like> likes;

    public Film() {
        this.genres = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public Film(Long id, String name, String description, LocalDate releaseDate, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new ArrayList<>();
    }

    public Film(Long id, String name, String description, LocalDate releaseDate, long duration, double rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.likes = new ArrayList<>();
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

    public Dictionary getRating() {
        return this.rating;
    }

    public void setRating(Dictionary rating) {
        this.rating = rating;
    }

    public void setLikeList(List<Like> likes) {
        if (likes == null) {
            this.likes = new ArrayList<>();
        } else {
            this.likes = likes.stream().distinct().collect(Collectors.toList());
        }
    }

    public void setGenreList(List<Genre> genres) {
        if (genres == null) {
            this.genres = new ArrayList<>();
        } else {
            this.genres = genres.stream().distinct().collect(Collectors.toList());
        }
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public List<Like> getLikes() {
        return likes;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getLikeCount() {
        return likes.size();
    }

    public Like getLikeById(Long likeId) {
        return likes.stream()
                .filter(l -> l.getId().equals(likeId))
                .findFirst()
                .orElse(null);
    }

    public void removeLike(Like like) {
        this.likes.remove(like);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }

    @Override
    public String toString() {
        return "Film(" +
                "id=" + this.getId() + ", " +
                "name=" + this.getName() + ", " +
                "description=" + this.getDescription() + ", " +
                "releaseDate=" + this.getReleaseDate().format(DateTimeFormatter.ISO_DATE) + ", " +
                "duration=" + this.getDuration() + ", " +
                "rate=" + this.getRate() + ")";
    }
}

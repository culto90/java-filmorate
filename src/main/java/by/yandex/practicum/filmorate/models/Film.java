package by.yandex.practicum.filmorate.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Film {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    private double rate;
    private List<Like> likes;

    public Film() {
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

    public void setLikeList(List<Like> likes) {
        this.likes = new ArrayList<>(likes);
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public List<Like> getLikes() {
        return likes;
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

    @Override
    public String toString() {
        return "Film(" +
                "id=" + this.getId() + ", " +
                "name=" + this.getName() + ", " +
                "description=" + this.getDescription() + ", " +
                "releaseDate=" + this.getReleaseDate().format(DateTimeFormatter.ISO_DATE) + ", " +
                "duration=" + this.getDuration() + ")";
    }
}

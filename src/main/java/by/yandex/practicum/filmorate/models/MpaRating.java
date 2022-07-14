package by.yandex.practicum.filmorate.models;

public class MpaRating{
    private Long id;
    private String name;
    private String description;

    public MpaRating() {
    }

    public MpaRating(Long id) {
        this.id = id;
    }

    public MpaRating(Long ratingId, String name, String description) {
        this.id = ratingId;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
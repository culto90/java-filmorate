package by.yandex.practicum.filmorate.models.dictionaries;

public class MpaRating implements Dictionary {
    private final static String DICTIONARY_NAME = "MPA Rating Dictionary";
    private Long ratingId;
    private String code;
    private String description;

    public MpaRating() {
    }

    public MpaRating(Long id) {
        this.ratingId = id;
    }

    public MpaRating(Long ratingId, String code, String description) {
        this.ratingId = ratingId;
        this.code = code;
        this.description = description;
    }

    public void setId(Long id) {
        this.ratingId = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long getId() {
        return ratingId;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return DICTIONARY_NAME;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}

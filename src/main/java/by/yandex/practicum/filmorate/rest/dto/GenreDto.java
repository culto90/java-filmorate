package by.yandex.practicum.filmorate.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreDto {
    private Long id;
    private String name;
    private String description;

    public GenreDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

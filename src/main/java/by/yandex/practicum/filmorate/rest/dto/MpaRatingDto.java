package by.yandex.practicum.filmorate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MpaRatingDto {
    private Long id;
    private String name;
    private String description;
}

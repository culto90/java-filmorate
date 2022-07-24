package by.yandex.practicum.filmorate.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {
    private Long id;
    @NotBlank(message = "Имя режиссёра не может быть пустым.")
    @NotNull
    private String name;
}

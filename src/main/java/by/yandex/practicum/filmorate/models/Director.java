package by.yandex.practicum.filmorate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director {
    private Long id;
    @NotBlank(message = "Имя режиссёра не может быть пустым.")
    @NotNull
    private String name;
}

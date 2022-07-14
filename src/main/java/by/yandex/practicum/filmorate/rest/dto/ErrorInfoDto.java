package by.yandex.practicum.filmorate.rest.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfoDto {
    private String url;
    private String exception;
}

package by.yandex.practicum.filmorate.rest.dto;

import lombok.Data;

@Data
public class ErrorInfoDto {
    private final String url;
    private final String exception;

    public ErrorInfoDto(String url, String exception) {
        this.url = url;
        this.exception = exception;
    }
}

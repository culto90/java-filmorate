package by.yandex.practicum.filmorate.models;

public class ErrorInfo {
    private final String url;
    private final String exception;

    public ErrorInfo(String url, String ex) {
        this.url = url;
        this.exception = ex;
    }

    public String getUrl() {
        return url;
    }

    public String getException() {
        return exception;
    }
}

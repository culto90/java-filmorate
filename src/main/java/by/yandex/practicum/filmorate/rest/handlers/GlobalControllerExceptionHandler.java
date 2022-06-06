package by.yandex.practicum.filmorate.rest.handlers;


import by.yandex.practicum.filmorate.exceptions.DtoConverterException;
import by.yandex.practicum.filmorate.exceptions.FilmServiceException;
import by.yandex.practicum.filmorate.exceptions.UserServiceException;
import by.yandex.practicum.filmorate.models.ErrorInfo;
import by.yandex.practicum.filmorate.rest.converters.ErrorInfoToErrorInfoDtoConverter;
import by.yandex.practicum.filmorate.rest.dto.ErrorInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorInfoToErrorInfoDtoConverter errorInfoDtoConverter;

    public GlobalControllerExceptionHandler(ErrorInfoToErrorInfoDtoConverter errorInfoDtoConverter) {
        this.errorInfoDtoConverter = errorInfoDtoConverter;
    }

    @ExceptionHandler(value = UserServiceException.class)
    protected ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = FilmServiceException.class)
    protected ResponseEntity<Object> handleFilmServiceException(FilmServiceException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = DtoConverterException.class)
    protected ResponseEntity<Object> handleFilmServiceException(DtoConverterException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        List<String> errList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String errorMessage = String.join(" ", errList);
        log.info(errorMessage);
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), errorMessage);
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return new ResponseEntity<>(errorInfoDto, headers, status);
    }

    private String getRequestURI(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest requestHttp = ((ServletWebRequest) request).getRequest();
            return String.format("%s %s", requestHttp.getMethod(), requestHttp.getRequestURI());
        } else {
            return "";
        }
    }

}

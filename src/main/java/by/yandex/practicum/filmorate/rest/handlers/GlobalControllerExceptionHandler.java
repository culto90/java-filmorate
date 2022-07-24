package by.yandex.practicum.filmorate.rest.handlers;


import by.yandex.practicum.filmorate.exceptions.*;
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

    @ExceptionHandler(value = GenreNotFoundException.class)
    protected ResponseEntity<Object> handleGenreNotFoundException(GenreNotFoundException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = FilmorateRepositoryException.class)
    protected ResponseEntity<Object> handleFilmorateRepositoryException(FilmorateRepositoryException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = MpaRatingNotFoundException.class)
    protected ResponseEntity<Object> handleMpaRatingNotFoundException(MpaRatingNotFoundException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = FilmNotFoundException.class)
    protected ResponseEntity<Object> handleFilmNotFoundException(FilmNotFoundException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
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

    @ExceptionHandler(value = DirectorServiceException.class)
    protected ResponseEntity<Object> handleDirectorServiceException(DirectorServiceException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = FilmStorageException.class)
    protected ResponseEntity<Object> handleFilmStorageException(FilmStorageException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = FriendshipNotFoundException.class)
    protected ResponseEntity<Object> handleFriendshipNotFoundException(FriendshipNotFoundException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = FriendshipServiceException.class)
    protected ResponseEntity<Object> handleFriendshipServiceException(FriendshipServiceException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = FriendshipStorageException.class)
    protected ResponseEntity<Object> handleFriendshipStorageException(FriendshipStorageException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = LikeNotFoundException.class)
    protected ResponseEntity<Object> handleLikeNotFoundException(LikeNotFoundException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = LikeServiceException.class)
    protected ResponseEntity<Object> handleLikeServiceException(LikeServiceException ex, WebRequest request) {
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

    @ExceptionHandler(value = UserStorageException.class)
    protected ResponseEntity<Object> handleUserStorageException(UserStorageException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = InvalidParameterException.class)
    protected ResponseEntity<Object> handleInvalidParameterException(InvalidParameterException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = DirectorNotFoundException.class)
    protected ResponseEntity<Object> handleDirectorNotFoundException(DirectorNotFoundException ex, WebRequest request) {
        log.info(ex.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(getRequestURI(request), ex.getMessage());
        ErrorInfoDto errorInfoDto = errorInfoDtoConverter.convert(errorInfo);
        return handleExceptionInternal(ex, errorInfoDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
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

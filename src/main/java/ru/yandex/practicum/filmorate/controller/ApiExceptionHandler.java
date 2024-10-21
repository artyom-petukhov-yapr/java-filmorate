package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException exception) {
        return exceptionHandler(exception, HttpStatus.NOT_FOUND, "Ресурс не найден");
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> validationException(ValidationException exception) {
        return exceptionHandler(exception, HttpStatus.BAD_REQUEST, "Некорректные данные");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(RuntimeException exception) {
        return exceptionHandler(exception, HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера");
    }

    private ResponseEntity<ErrorResponse> exceptionHandler(Exception e, HttpStatus httpStatus, String error) {
        // централизованное логирование ошибки
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorResponse(error, e.getMessage()));
    }
}

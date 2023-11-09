package com.example.marketpalce.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.StreamSupport;

/**
 * The Class ApplicationExceptionAdvice
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@RestControllerAdvice
public class ApplicationExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandlingFields handleMethodArgumentException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));


        return new ErrorHandlingFields(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                       request.getServletPath(), request.getMethod(), errors
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandlingFields handleConstraintViolationExcpetion(ConstraintViolationException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        exception.getConstraintViolations().forEach(error -> errors.put(
                Objects.requireNonNull(StreamSupport.stream(error.getPropertyPath().spliterator(), false)
                                               .reduce((first, second) -> second).orElse(null)).toString(), error.getMessage()));

        return new ErrorHandlingFields(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                       request.getServletPath(), request.getMethod(), errors
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorHandling handleServletRequestParameterException(MissingServletRequestParameterException exception, HttpServletRequest request) {
        return new ErrorHandling(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), request.getServletPath(), request.getMethod(),
                exception.getParameterName().concat(" é obrigatório na requisição")
        );
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorHandling> handleUserException(GlobalException exception, HttpServletRequest request) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(new ErrorHandling(LocalDateTime.now(), exception.getHttpStatus().value(), request.getServletPath(), request.getMethod(),
                                        exception.getMessage()
                ));
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorHandling> handleUserException(BadRequest exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorHandling(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), request.getServletPath(), request.getMethod(),
                                        exception.getMessage()
                ));
    }

    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<ErrorHandling> handleUserException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorHandling(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), request.getServletPath(), request.getMethod(),
                                        "Sem autorização verifique seu login e password"
                ));
    }

    public record ErrorHandlingFields(LocalDateTime timestamps, Integer statusCode, String message, String path, String method,
                                      Map<String, String> errors) {
    }

    public record ErrorHandling(LocalDateTime timestamps, Integer statusCode, String path, String method, String message) {
    }
}

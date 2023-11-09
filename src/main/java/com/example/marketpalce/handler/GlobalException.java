package com.example.marketpalce.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * The Class GlobalException
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Getter
public class GlobalException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4261107194589046795L;

    private final String message;

    private final HttpStatus httpStatus;

    public GlobalException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

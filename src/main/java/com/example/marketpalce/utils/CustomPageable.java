package com.example.marketpalce.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;

/**
 * The Class CustomPageable
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
public class CustomPageable extends PageRequest {

    @Serial
    private static final long serialVersionUID = -7319661590239232263L;

    protected CustomPageable(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize, sort);
    }

    public static Pageable getInstance(Integer page, Integer size, final String sorting) {
        page = Optional.ofNullable(page).orElse(0);
        size = Optional.ofNullable(size).orElse(10);

        if (Objects.isNull(sorting) || sorting.isBlank()) {
            return PageRequest.of(page, size);
        }

        final Sort direction = sorting.startsWith("-") ?
                Sort.by(Sort.Direction.DESC, sorting.replace("-", "")) :
                Sort.by(Sort.Direction.ASC, sorting);

        return PageRequest.of(page, size, direction);
    }
}

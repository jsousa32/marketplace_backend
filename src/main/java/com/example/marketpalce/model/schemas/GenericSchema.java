package com.example.marketpalce.model.schemas;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The Class GenericSchema
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@MappedSuperclass
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class GenericSchema implements Serializable {

    @Serial
    private static final long serialVersionUID = -5825084685226599393L;

    private Boolean disabled;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "disabled_at")
    private LocalDateTime disabledAt;
}

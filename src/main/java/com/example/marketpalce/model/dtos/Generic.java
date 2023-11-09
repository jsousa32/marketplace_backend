package com.example.marketpalce.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The Class Generic
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Generic implements Serializable {

    @Serial
    private static final long serialVersionUID = -4852436778144706532L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean disabled = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime disabledAt;

    public void setDisabled(Boolean disabled) {
        this.disabledAt = disabled ? LocalDateTime.now() : null;

        this.updatedAt = LocalDateTime.now();

        this.disabled = disabled;
    }
}

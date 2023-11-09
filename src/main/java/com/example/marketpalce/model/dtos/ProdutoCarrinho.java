package com.example.marketpalce.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Class CarrinhoProduto
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCarrinho {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "O produto id não pode ser branco ou nulo.")
    private UUID produtoId;

    @NotBlank(message = "A quantidade de produto não pode ser branco ou nulo.")
    private Integer quantidadeProduto;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal total;
}

package com.example.marketpalce.model.dtos;

import com.example.marketpalce.model.enums.CarrinhoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The Class Carrinho
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoCompra extends Generic {

    @Serial
    private static final long serialVersionUID = 2996843537469724734L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotEmpty(message = "O carrinho deve possuir ao menos um produto")
    @Valid
    private List<ProdutoCarrinho> produtoCarrinho;

    @NotBlank(message = "O usuario id no carrinho de compras não pode ser branco ou nulo.")
    private UUID enderecoId;

    @NotBlank(message = "O usuario id no carrinho de compras não pode ser branco ou nulo.")
    private String usuarioId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal total;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private CarrinhoEnum statusCarrinho;
}

package com.example.marketpalce.model.dtos;

import com.example.marketpalce.model.schemas.GenericSchema;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
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
public class CarrinhoCompra extends GenericSchema {

    @Serial
    private static final long serialVersionUID = 2996843537469724734L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotEmpty(message = "O carrinho deve possuir ao menos um produto")
    @Valid
    private List<ProdutoCarrinho> produtos;

    @Valid
    private Endereco endereco;

    @NotBlank(message = "O usuario id no carrinho de compras não pode ser branco ou nulo.")
    private String usuarioId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String total;
}

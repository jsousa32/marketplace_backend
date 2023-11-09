package com.example.marketpalce.model.dtos;

import com.example.marketpalce.model.enums.ProdutoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Class Produto
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto extends Generic{

    @Serial
    private static final long serialVersionUID = -148652173840735903L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "O nome do produto não pode ser nulo ou em branco.")
    private String nome;

    @NotBlank(message = "O preço do produto não pode ser nulo ou em branco.")
    private BigDecimal preco;

    @NotBlank(message = "O nome do produto não pode ser nulo ou em branco.")
    private Double estoque;

    @NotBlank(message = "O nome do produto não pode ser nulo ou em branco.")
    private Double codigoBarras;

    @Enumerated(EnumType.STRING)
    private ProdutoEnum tipoProduto;

    private byte[] imagemProduto;
}

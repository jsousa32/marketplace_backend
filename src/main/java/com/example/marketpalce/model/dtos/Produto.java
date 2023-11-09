package com.example.marketpalce.model.dtos;

import com.example.marketpalce.model.enums.ProdutoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
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
    @Pattern(regexp = "[0-9.,]$", message = "O preço do produto está inválido")
    private String preco;

    @NotBlank(message = "O nome do produto não pode ser nulo ou em branco.")
    @Pattern(regexp = "[0-9]$", message = "O preço do produto está inválido")
    private String estoque;

    @NotBlank(message = "O nome do produto não pode ser nulo ou em branco.")
    @Pattern(regexp = "[0-9]{8}$", message = "O preço do produto está inválido")
    private String codigoBarras;

    @Enumerated(EnumType.STRING)
    private ProdutoEnum tipoProduto;

    private byte[] imagemProduto;
}

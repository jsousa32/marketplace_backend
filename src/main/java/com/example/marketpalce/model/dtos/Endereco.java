package com.example.marketpalce.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * The Class Endereco
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "O cep do endereço não pode ser branco ou nulo.")
    @Pattern(regexp = "[0-9]{8}$", message = "O cep está no formato inválido, apenas números")
    @Schema(defaultValue = "37985468")
    private String cep;

    @NotBlank(message = "A cidade do endereço não pode ser branco ou nulo.")
    @Schema(defaultValue = "Itajubá")
    private String cidade;

    @NotBlank(message = "O bairro do endereço não pode ser branco ou nulo.")
    @Schema(defaultValue = "Centro")
    private String bairro;

    @NotBlank(message = "O logradouro do endereço não pode ser branco ou nulo.")
    @Schema(defaultValue = "Alcides Faria")
    private String logradouro;

    @NotBlank(message = "O numero do endereço não pode ser branco ou nulo.")
    @Pattern(regexp = "[0-9]{0,5}$", message = "O numero está no formato inválido, apenas números")
    @Schema(defaultValue = "69")
    private String numero;

    private String complemento;
}

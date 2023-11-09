package com.example.marketpalce.model.dtos;

import com.example.marketpalce.model.enums.UsuarioEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

/**
 * The Class Usuario
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends Generic {

    @Serial
    private static final long serialVersionUID = -5666354280363782279L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank(message = "O nome do usuário não pode ser nulo ou em branco.")
    @Schema(defaultValue = "João")
    private String nome;

    @NotBlank(message = "O sobrenome do usuário não pode ser nulo ou em branco.")
    @Schema(defaultValue = "Sousa")
    private String sobrenome;

    @NotBlank(message = "O telefone do usuário não pode ser nulo ou em branco.")
    @Pattern(regexp = "[0-9]{11}$", message = "O formato do telefone está incorreto")
    @Schema(defaultValue = "35987654321")
    private String telefone;

    @Email(message = "O email está inválido")
    @Schema(defaultValue = "joao@gmail.com")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "O sobrenome do usuário não pode ser nulo ou em branco.")
    @Schema(defaultValue = "12345")
    private String senha;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UsuarioEnum tipoAcesso = UsuarioEnum.USUARIO;

    @NotEmpty(message = "O usuário deve possuir ao menos um endereco")
    @Valid
    private List<Endereco> enderecos;

    @JsonIgnore
    private String tokenEmail;

    @JsonIgnore
    private String tokenSenha;

    @JsonIgnore
    private Boolean emailConfirmado;
}

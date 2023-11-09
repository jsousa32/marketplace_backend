package com.example.marketpalce.model.schemas;

import com.example.marketpalce.model.enums.UsuarioEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Set;

/**
 * The Class UsuarioSchema
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_usuarios")
public class UsuarioSchema extends GenericSchema {

    @Serial
    private static final long serialVersionUID = -1573511193544715368L;

    @Id
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String telefone;

    @Column(name = "tipo_acesso")
    @Enumerated(EnumType.STRING)
    private UsuarioEnum tipoAcesso;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_endereco_x_usuario"), nullable = false)
    private Set<EnderecoSchema> enderecos;

    @Column(name = "token_email", nullable = false)
    private String tokenEmail;

    @Column(name = "token_senha")
    private String tokenSenha;

    @Column(name = "email_confirmado")
    private Boolean emailConfirmado;
}

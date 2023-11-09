package com.example.marketpalce.model.schemas;

import com.example.marketpalce.model.enums.ProdutoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Class ProdutoSchema
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_produtos")
public class ProdutoSchema extends GenericSchema {

    @Serial
    private static final long serialVersionUID = 5227907559299155792L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Double estoque;

    @Column(name = "codigo_barras", nullable = false)
    private Double codigoBarras;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_produto", nullable = false)
    private ProdutoEnum tipoProduto;

    @Column(name = "imagem_produto", nullable = false)
    private byte[] imagemProduto;
}

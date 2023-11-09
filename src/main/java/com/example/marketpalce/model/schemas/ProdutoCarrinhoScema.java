package com.example.marketpalce.model.schemas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The Class ProdutoCarinhoScema
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_produto_carrinho")
public class ProdutoCarrinhoScema {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "produto_id", nullable = false)
    private UUID produtoId;

    @Column(name = "quantidade_produto", nullable = false)
    private Integer quantidadeProduto;

    @Column(nullable = false)
    private BigDecimal total;
}

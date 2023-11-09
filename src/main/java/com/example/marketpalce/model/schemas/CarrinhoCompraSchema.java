package com.example.marketpalce.model.schemas;

import com.example.marketpalce.model.enums.CarrinhoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The Class CarrinhoCompraSchema
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_carrinho_compras")
public class CarrinhoCompraSchema extends GenericSchema {

    @Serial
    private static final long serialVersionUID = -6580214976322196574L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "carrinho_compra_id", foreignKey = @ForeignKey(name = "fk_compras_x_produtos"), nullable = false)
    private List<ProdutoCarrinhoScema> produtoCarrinho;

    @Column(nullable = false)
    private UUID enderecoId;

    @Column(nullable = false)
    private String usuarioId;

    @Column(nullable = false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private CarrinhoEnum statusCarrinho;
}

package com.example.marketpalce.business;

import com.example.marketpalce.handler.GlobalException;
import com.example.marketpalce.model.dtos.*;
import com.example.marketpalce.model.enums.CarrinhoEnum;
import com.example.marketpalce.model.schemas.CarrinhoCompraSchema;
import com.example.marketpalce.model.schemas.ProdutoSchema;
import com.example.marketpalce.repository.CarrinhoCompraRepository;
import com.example.marketpalce.repository.ProdutoRepository;
import com.example.marketpalce.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.marketpalce.utils.MapperUtils.mapper;
import static com.example.marketpalce.utils.MapperUtils.mapperList;

/**
 * The Class CarrinhoCompraBusiness
 *
 * @author João Lucas Silva de Sousa.
 * @since 09/11/2023.
 */
@Component
public class CarrinhoCompraBusiness {

    private final UsuarioRepository usuarioRepository;

    private final CarrinhoCompraRepository carrinhoCompraRepository;

    private final ProdutoRepository produtoRepository;

    public CarrinhoCompraBusiness(UsuarioRepository usuarioRepository,
                                  CarrinhoCompraRepository carrinhoCompraRepository,
                                  ProdutoRepository produtoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carrinhoCompraRepository = carrinhoCompraRepository;
        this.produtoRepository = produtoRepository;
    }


    public void save(CarrinhoCompra request) {

        preProcessaCarrinhoCompra(request);

        Usuario usuario = mapper(usuarioRepository.getReferenceById(request.getUsuarioId()), Usuario.class);

        if (Objects.isNull(usuario)) {
            throw new GlobalException("Usuario não encontrado", HttpStatus.NOT_FOUND);
        }

        request.setStatusCarrinho(CarrinhoEnum.PENDENTE);

        usuario.getEnderecos()
                .stream()
                .filter(e -> e != null && e.getId() != null && e.getId().equals(request.getEnderecoId()))
                .findFirst()
                .orElseThrow(() -> new GlobalException("Endereco referente ao usuario não encontrado", HttpStatus.NOT_FOUND));

        saveCarrinhoCompra(request);
    }

    public Page<CarrinhoCompra> findAllByUsuarioIdAndDisabled(String usuarioId, Boolean disabled, Pageable instance) {
        return carrinhoCompraRepository.findAllByUsuarioIdAndDisabled(usuarioId, disabled, instance)
                .map(c -> mapper(c, CarrinhoCompra.class));
    }

    public CarrinhoCompra findByIdAndDisabled(UUID carrinhoId, Boolean disabled) {
        return carrinhoCompraRepository.findByIdAndDisabled(carrinhoId, disabled)
                .map(c -> mapper(c, CarrinhoCompra.class))
                .orElseThrow(() -> new GlobalException("Carrinho de compras não encontrado", HttpStatus.NOT_FOUND));
    }

    public void update(UUID carrinhoId, CarrinhoCompra request) {
        if (request.getStatusCarrinho().equals(CarrinhoEnum.CANCELADO)) preProcessaCarrinhoCancelamento(request);
        else preProcessaCarrinhoCompra(request);

        CarrinhoCompra carrinhoCompra = findByIdAndDisabled(carrinhoId, false);

        BeanUtils.copyProperties(request, carrinhoCompra);

        saveCarrinhoCompra(carrinhoCompra);
    }


    public void delete(UUID carrinhoId) {
        CarrinhoCompra carrinhoCompra = findByIdAndDisabled(carrinhoId, false);

        carrinhoCompra.setDisabled(true);

        saveCarrinhoCompra(carrinhoCompra);
    }

    private void preProcessaCarrinhoCompra(CarrinhoCompra request) {
        BigDecimal total = request.getTotal();
        List<Produto> produtosAtualizados = new ArrayList<>();

        for (ProdutoCarrinho produtoCarrinho : request.getProdutoCarrinho()) {

            Produto produto = mapper(produtoRepository.getReferenceById(produtoCarrinho.getProdutoId()), Produto.class);

            total = total.add(produto.getPreco().multiply(new BigDecimal(produtoCarrinho.getQuantidadeProduto())));

            produto.setEstoque(produto.getEstoque() - produtoCarrinho.getQuantidadeProduto());

            produtosAtualizados.add(produto);
        }

        produtoRepository.saveAll(mapperList(produtosAtualizados, ProdutoSchema.class));

        request.setTotal(total);
    }

    private void preProcessaCarrinhoCancelamento(CarrinhoCompra request) {
        List<Produto> produtosAtualizados = new ArrayList<>();

        for (ProdutoCarrinho produtoCarrinho : request.getProdutoCarrinho()) {

            Produto produto = mapper(produtoRepository.getReferenceById(produtoCarrinho.getProdutoId()), Produto.class);

            produto.setEstoque(produto.getEstoque() + produtoCarrinho.getQuantidadeProduto());

            produtosAtualizados.add(produto);
        }

        produtoRepository.saveAll(mapperList(produtosAtualizados, ProdutoSchema.class));
    }

    private void saveCarrinhoCompra(CarrinhoCompra request) {
        carrinhoCompraRepository.save(mapper(request, CarrinhoCompraSchema.class));
    }
}

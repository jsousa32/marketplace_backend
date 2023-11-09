package com.example.marketpalce.business;

import com.example.marketpalce.handler.GlobalException;
import com.example.marketpalce.model.dtos.Produto;
import com.example.marketpalce.model.schemas.ProdutoSchema;
import com.example.marketpalce.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static com.example.marketpalce.utils.MapperUtils.mapper;

/**
 * The Class ProdutoBusiness
 *
 * @author João Lucas Silva de Sousa.
 * @since 09/11/2023.
 */
@Component
public class ProdutoBusiness {

    private final ProdutoRepository produtoRepository;

    public ProdutoBusiness(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void save(Produto request) {
        Boolean isExist = produtoRepository.existsByCodigoBarrasAndDisabled(request.getCodigoBarras(), false);

        if (isExist) {
            throw new GlobalException("O produto com o código de barras" + request.getCodigoBarras() + " já está cadastrado.", HttpStatus.CONFLICT);
        }

        saveProduto(request);
    }

    public Page<Produto> findAllAndDisabled(Boolean disabeld, Pageable instance) {
        return produtoRepository.findAllByDisabled(disabeld, instance)
                .map(p -> mapper(p, Produto.class));
    }

    public Produto findByIdAndDisabled(UUID produtoId, Boolean disabled) {
        return produtoRepository.findByIdAndDisabled(produtoId, disabled)
                .map(p -> mapper(p, Produto.class))
                .orElseThrow(() -> new GlobalException("Produto não encontrado", HttpStatus.NOT_FOUND));
    }

    public void update(UUID produtoId, Produto request) {
        Produto produto = findByIdAndDisabled(produtoId, false);

        if (Objects.isNull(produto)) {
            throw new GlobalException("Produto não encontrado", HttpStatus.NOT_FOUND);
        }

        BeanUtils.copyProperties(request, produto);

        saveProduto(produto);
    }

    public void delete(UUID produtoId) {
        Produto produto = findByIdAndDisabled(produtoId, false);

        produto.setDisabled(true);

        saveProduto(produto);
    }

    private void saveProduto(Produto produto) {
        produtoRepository.save(mapper(produto, ProdutoSchema.class));
    }
}

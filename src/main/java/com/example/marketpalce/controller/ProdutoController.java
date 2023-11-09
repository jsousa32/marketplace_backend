package com.example.marketpalce.controller;

import com.example.marketpalce.business.ProdutoBusiness;
import com.example.marketpalce.model.dtos.Produto;
import com.example.marketpalce.utils.CustomPageable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The Class ProdutoController
 *
 * @author João Lucas Silva de Sousa.
 * @since 09/11/2023.
 */
@RestController
@RequestMapping("products")
@Tag(name = "Modulo Produto")
public class ProdutoController {

    private final ProdutoBusiness produtoBusiness;

    public ProdutoController(ProdutoBusiness produtoBusiness) {
        this.produtoBusiness = produtoBusiness;
    }



    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Produto request) {
        produtoBusiness.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<Produto>> findAllAndDisabled(
            @RequestParam(name = "disabled") @NotBlank(message = "O estado do produto não pode ser nulo na requisição") final Boolean disabled,
            @RequestParam(name = "page", required = false) final Integer page,
            @RequestParam(name = "size", required = false) final Integer size,
            @RequestParam(name = "sorting", required = false) final String sorting) {

        return ResponseEntity.status(HttpStatus.OK).body(
                produtoBusiness.findAllAndDisabled(disabled, CustomPageable.getInstance(page, size, sorting)));
    }

    @GetMapping("details")
    public ResponseEntity<Produto> findByIdAndDisabled(
            @RequestParam(name = "produto_id") @NotBlank(message = "O produto id não pode ser nulo na requisição") final UUID produtoId,
            @RequestParam(name = "disabled") @NotBlank(message = "O estado do produto não pode ser nulo na requisição") final Boolean disabled) {

        return ResponseEntity.status(HttpStatus.OK).body(produtoBusiness.findByIdAndDisabled(produtoId, disabled));
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestParam(name = "produto_id") @NotBlank(message = "O produto id não pode ser nulo na requisição") final UUID produtoId,
            @RequestBody @Valid Produto request) {

        produtoBusiness.update(produtoId, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam(name = "produto_id") @NotBlank(message = "O produto id não pode ser nulo na requisição") final UUID produtoId) {

        produtoBusiness.delete(produtoId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

package com.example.marketpalce.controller;

import com.example.marketpalce.business.CarrinhoCompraBusiness;
import com.example.marketpalce.model.dtos.CarrinhoCompra;
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
 * The Class CarrinhoCompraController
 *
 * @author João Lucas Silva de Sousa.
 * @since 09/11/2023.
 */
@RestController
@RequestMapping("shopping-cart")
@Tag(name = "Modulo Carrinho Compra")
public class CarrinhoCompraController {

    private final CarrinhoCompraBusiness carrinhoCompraBusiness;

    public CarrinhoCompraController(CarrinhoCompraBusiness carrinhoCompraBusiness) {
        this.carrinhoCompraBusiness = carrinhoCompraBusiness;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CarrinhoCompra request) {

        carrinhoCompraBusiness.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<CarrinhoCompra>> findAllByUsuarioId(
            @RequestParam(name = "usuario_id") @NotBlank(message = "O estado do produto não pode ser nulo na requisição") final String usuarioId,
            @RequestParam(name = "disabled") @NotBlank(message = "O estado do produto não pode ser nulo na requisição") final Boolean disabled,
            @RequestParam(name = "page", required = false) final Integer page,
            @RequestParam(name = "size", required = false) final Integer size,
            @RequestParam(name = "sorting", required = false) final String sorting) {

        return ResponseEntity.status(HttpStatus.OK).body(
                carrinhoCompraBusiness.findAllByUsuarioIdAndDisabled(usuarioId, disabled, CustomPageable.getInstance(page, size, sorting)));
    }

    @GetMapping("details")
    public ResponseEntity<CarrinhoCompra> findById(
            @RequestParam(name = "carrinho_id") @NotBlank(message = "O estado do produto não pode ser nulo na requisição") final UUID carrinhoId,
            @RequestParam(name = "disabled") @NotBlank(message = "O estado do produto não pode ser nulo na requisição") final Boolean disabled) {

        return ResponseEntity.status(HttpStatus.OK).body(
                carrinhoCompraBusiness.findByIdAndDisabled(carrinhoId, disabled));
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestParam(name = "carrinho_id") @NotBlank(message = "O estado do produto não pode ser nulo na requisição") final UUID carrinhoId,
            @RequestBody @Valid CarrinhoCompra request) {

        carrinhoCompraBusiness.update(carrinhoId, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<?> update(
            @RequestParam(name = "carrinho_id") @NotBlank(message = "O estado do produto não pode ser nulo na requisição") final UUID carrinhoId) {

        carrinhoCompraBusiness.delete(carrinhoId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

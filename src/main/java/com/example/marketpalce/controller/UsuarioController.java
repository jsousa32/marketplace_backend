package com.example.marketpalce.controller;

import com.example.marketpalce.business.UsuarioBusiness;
import com.example.marketpalce.model.dtos.Usuario;
import com.example.marketpalce.utils.CustomPageable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The Class UsuarioController
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@RestController
@RequestMapping("user")
@Tag(name = "Modulo Usuário")
public class UsuarioController {

    private final UsuarioBusiness usuarioBusiness;

    public UsuarioController(UsuarioBusiness usuarioBusiness) {
        this.usuarioBusiness = usuarioBusiness;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Usuario usuario) {
        usuarioBusiness.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> findAllAndDisabled(
            @RequestParam(name = "disabled") @NotBlank(message = "O estado dos usuarios não pode ser nulo na requisição") final Boolean disabled,
            @RequestParam(name = "page", required = false) final Integer page,
            @RequestParam(name = "size", required = false) final Integer size,
            @RequestParam(name = "sorting", required = false) final String sorting) {

        return ResponseEntity.status(HttpStatus.OK).body(
                usuarioBusiness.findAllAndDisabled(disabled, CustomPageable.getInstance(page, size, sorting)));
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestParam(name = "usuario_id") @NotBlank(message = "O usuario id não pode ser nulo na requisição") final String usuarioId,
            @RequestBody @Valid Usuario usuario) {

        usuarioBusiness.update(usuarioId, usuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam(name = "usuario_id") @NotBlank(message = "O usuario id não pode ser nulo na requisição") final String usuarioId) {

        usuarioBusiness.delete(usuarioId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

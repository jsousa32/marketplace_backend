package com.example.marketpalce.controller;

import com.example.marketpalce.business.UsuarioBusiness;
import com.example.marketpalce.handler.ApplicationExceptionAdvice;
import com.example.marketpalce.model.dtos.Usuario;
import com.example.marketpalce.service.KeycloakInstanceService;
import com.example.marketpalce.utils.CustomPageable;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final KeycloakInstanceService keycloakInstanceService;

    public UsuarioController(UsuarioBusiness usuarioBusiness,
                             KeycloakInstanceService keycloakInstanceService) {
        this.usuarioBusiness = usuarioBusiness;
        this.keycloakInstanceService = keycloakInstanceService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(schema = @Schema(implementation = ApplicationExceptionAdvice.ErrorHandling.class))}),
    })
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Usuario usuario) {
        usuarioBusiness.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "301", description = "Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(schema = @Schema(implementation = ApplicationExceptionAdvice.ErrorHandling.class))}),
    })
    @GetMapping
    public ResponseEntity<Page<Usuario>> findAll(
            @RequestParam(name = "page", required = false) final Integer page,
            @RequestParam(name = "size", required = false) final Integer size,
            @RequestParam(name = "sorting", required = false) final String sorting) {

        return ResponseEntity.status(HttpStatus.FOUND).body(
                usuarioBusiness.findAll(CustomPageable.getInstance(page, size, sorting)));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(schema = @Schema(implementation = ApplicationExceptionAdvice.ErrorHandling.class))}),
    })
    @PutMapping
    public ResponseEntity<?> update(
            @RequestParam(name = "usuario_id") @NotBlank(message = "O usuario id não pode ser nullo na requisição") final String usuarioId,
            @RequestBody @Valid Usuario usuario) {

        usuarioBusiness.update(usuarioId, usuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(schema = @Schema(implementation = ApplicationExceptionAdvice.ErrorHandling.class))}),
    })
    @DeleteMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> delete(
            @RequestParam(name = "usuario_id") @NotBlank(message = "O usuario id não pode ser nullo na requisição") final String usuarioId) {

        usuarioBusiness.delete(usuarioId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("roles")
    public List<RoleRepresentation> roles() {
        return keycloakInstanceService.getRealmRoles();
    }
}

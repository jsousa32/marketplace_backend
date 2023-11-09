package com.example.marketpalce.business;

import com.example.marketpalce.handler.GlobalException;
import com.example.marketpalce.model.dtos.Usuario;
import com.example.marketpalce.model.schemas.UsuarioSchema;
import com.example.marketpalce.repository.UsuarioRepository;
import com.example.marketpalce.service.KeycloakInstanceService;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

import static com.example.marketpalce.utils.MapperUtils.mapper;
import static com.example.marketpalce.utils.MapperUtils.userRepresentantion;

/**
 * The Class UsuarioBusiness
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
@Component
public class UsuarioBusiness {

    private final UsuarioRepository usuarioRepository;
    private final KeycloakInstanceService keycloakInstanceService;

    public UsuarioBusiness(UsuarioRepository usuarioRepository,
                           KeycloakInstanceService keycloakInstanceService) {
        this.usuarioRepository = usuarioRepository;
        this.keycloakInstanceService = keycloakInstanceService;
    }

    public void save(Usuario usuario) {
        Boolean isExists = usuarioRepository.existsByEmail(usuario.getEmail());

        if (isExists) {
            throw new GlobalException("O email já está cadastrado", HttpStatus.CONFLICT);
        }

        UserRepresentation userRepresentation = userRepresentantion(usuario);

        UsersResource userResource = keycloakInstanceService.getUserResource();

        Response response = userResource.create(userRepresentation);

        if (response.getStatus() != 201) {
            throw new GlobalException("Problemas ao criar o usuário no keycloak", HttpStatus.CONFLICT);
        }

        String location = response.getMetadata().get("Location").toString().replace("]", "");

        int index = location.lastIndexOf("users/");

        String usuarioIdKeycloak = location.substring(index + "users/".length());

        Integer tokenEmail = new Random().nextInt(999999);

        usuario.setId(usuarioIdKeycloak);
        usuario.setTokenEmail(tokenEmail.toString());

        saveUsuario(usuario);

    }

    public Page<Usuario> findAllAndDisabled(Boolean disabled, Pageable pageable) {
        return usuarioRepository.findAllByDisabled(disabled, pageable)
                .map(u -> mapper(u, Usuario.class));
    }

    public Usuario findByIdAndDisabled(String usuarioId, Boolean disabled) {
        return usuarioRepository.findByIdAndDisabled(usuarioId, disabled)
                .map(u -> mapper(u, Usuario.class))
                .orElseThrow(() -> new GlobalException("Usuario não encontrado.", HttpStatus.NOT_FOUND));
    }

    public void update(String usuarioId, Usuario usuario) {
        Usuario usuarioDatabase = mapper(usuarioRepository.getReferenceById(usuarioId), Usuario.class);

        if (Objects.isNull(usuarioDatabase)) {
            throw new GlobalException("Não foi possível localizar o usuário com o Id " + usuarioId, HttpStatus.BAD_REQUEST);
        }

        BeanUtils.copyProperties(usuario, usuarioDatabase);

        UserRepresentation userRepresentation = userRepresentantion(usuarioDatabase);

        UserResource userResource = keycloakInstanceService.getUserResource().get(usuarioDatabase.getId());

        userResource.update(userRepresentation);

        saveUsuario(usuarioDatabase);
    }

    public void delete(String usuarioId) {
        Usuario usuario = findByIdAndDisabled(usuarioId, false);

        if (Objects.isNull(usuario)) {
            throw new GlobalException("Não foi possível localizar o usuário com o Id " + usuarioId, HttpStatus.BAD_REQUEST);
        }

        usuario.setDisabled(true);

        UserRepresentation userRepresentation = userRepresentantion(usuario);

        UserResource userResource = keycloakInstanceService.getUserResource().get(usuario.getId());

        userResource.update(userRepresentation);

        saveUsuario(usuario);
    }

    private void saveUsuario(Usuario usuarioDatabase) {
        usuarioRepository.save(mapper(usuarioDatabase, UsuarioSchema.class));
    }
}

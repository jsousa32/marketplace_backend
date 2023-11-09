package com.example.marketpalce.repository;

import com.example.marketpalce.model.schemas.UsuarioSchema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The Interface UsuarioRepository
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
public interface UsuarioRepository extends JpaRepository<UsuarioSchema, String> {

    Boolean existsByEmail(String email);

    Page<UsuarioSchema> findAllByDisabled(Boolean disabled, Pageable pageable);

    Optional<UsuarioSchema> findByIdAndDisabled(String usuarioId, Boolean disabled);
}

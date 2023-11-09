package com.example.marketpalce.repository;

import com.example.marketpalce.model.schemas.UsuarioSchema;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface UsuarioRepository
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
public interface UsuarioRepository extends JpaRepository<UsuarioSchema, String> {

    Boolean existsByEmail(String email);
}

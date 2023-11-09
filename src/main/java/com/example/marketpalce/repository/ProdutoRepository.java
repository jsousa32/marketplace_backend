package com.example.marketpalce.repository;

import com.example.marketpalce.model.schemas.ProdutoSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * The Interface ProdutoRepository
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
public interface ProdutoRepository extends JpaRepository<ProdutoSchema, UUID> {
}

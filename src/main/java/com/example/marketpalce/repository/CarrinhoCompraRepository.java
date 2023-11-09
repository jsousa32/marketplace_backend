package com.example.marketpalce.repository;

import com.example.marketpalce.model.schemas.CarrinhoCompraSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * The Interface CarrinhoCompraRepository
 *
 * @author João Lucas Silva de Sousa.
 * @since 08/11/2023.
 */
public interface CarrinhoCompraRepository extends JpaRepository<CarrinhoCompraSchema, UUID> {
}

package com.sabor_gourmet.restaurante_api.repositories;

import com.sabor_gourmet.restaurante_api.models.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    // Métodos de búsqueda como 'findByEstado'
}
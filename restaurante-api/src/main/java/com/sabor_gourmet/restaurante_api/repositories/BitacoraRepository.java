package com.sabor_gourmet.restaurante_api.repositories;

import com.sabor_gourmet.restaurante_api.models.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
}
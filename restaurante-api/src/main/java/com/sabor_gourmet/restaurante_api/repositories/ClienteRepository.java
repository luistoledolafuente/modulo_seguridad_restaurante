package com.sabor_gourmet.restaurante_api.repositories;

import com.sabor_gourmet.restaurante_api.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // Puedes agregar búsquedas personalizadas aquí si es necesario
}
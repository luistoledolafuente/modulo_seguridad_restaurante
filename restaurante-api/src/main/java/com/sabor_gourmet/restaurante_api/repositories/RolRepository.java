package com.sabor_gourmet.restaurante_api.repositories;

import com.sabor_gourmet.restaurante_api.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombreRol(String nombreRol);
}
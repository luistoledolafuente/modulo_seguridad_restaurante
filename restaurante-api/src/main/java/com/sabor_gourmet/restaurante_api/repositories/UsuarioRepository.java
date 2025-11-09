package com.sabor_gourmet.restaurante_api.repositories;

import com.sabor_gourmet.restaurante_api.models.Rol;
import com.sabor_gourmet.restaurante_api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    List<Usuario> findAllByRol(Rol rol);
}
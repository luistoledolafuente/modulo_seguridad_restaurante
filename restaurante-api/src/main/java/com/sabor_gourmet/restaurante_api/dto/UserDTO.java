package com.sabor_gourmet.restaurante_api.dto;

import com.sabor_gourmet.restaurante_api.models.Usuario;
import lombok.Data;

// DTO para enviar datos del usuario al frontend (SIN contraseña)
@Data
public class UserDTO {

    private Integer idUsuario;
    private String nombreUsuario;
    private String rol;
    private String estado;

    // Constructor para mapear fácil
    public UserDTO(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombreUsuario = usuario.getUsername();
        this.rol = usuario.getRol().getNombreRol();
        this.estado = usuario.getEstado();
    }
}
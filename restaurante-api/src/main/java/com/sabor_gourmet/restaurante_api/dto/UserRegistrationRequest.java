package com.sabor_gourmet.restaurante_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

// Este DTO recibirá los datos para crear cualquier tipo de usuario
@Data
@NoArgsConstructor
public class UserRegistrationRequest {

    private String nombreUsuario;
    private String contrasena;
    private String nombreRol; // Aquí enviaremos "ROLE_MOZO", "ROLE_CAJERO", etc.
}
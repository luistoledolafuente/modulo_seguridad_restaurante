package com.sabor_gourmet.restaurante_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getters, Setters, etc.
@NoArgsConstructor
public class LoginRequest {

    private String nombreUsuario;
    private String contrasena;
}
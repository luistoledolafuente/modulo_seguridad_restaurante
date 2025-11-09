package com.sabor_gourmet.restaurante_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private String tipo = "Bearer";
    private String nombreUsuario;
    private String rol;

    public JwtResponse(String token, String nombreUsuario, String rol) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }
}
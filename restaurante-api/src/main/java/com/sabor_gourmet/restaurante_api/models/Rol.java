package com.sabor_gourmet.restaurante_api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(name = "nombre_rol", nullable = false, unique = true)
    private String nombreRol; // Ej: ROLE_ADMIN

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

    public Rol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
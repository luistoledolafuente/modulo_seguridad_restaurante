package com.sabor_gourmet.restaurante_api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente; // Mapea a id_cliente

    @Column(length = 8, unique = true)
    private String dni;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(length = 15)
    private String telefono;

    @Column(length = 100)
    private String correo;

    @Column(nullable = false, length = 10)
    private String estado = "ACTIVO";

    // Sobrescribimos toString() para que la Bitácora (AOP) sea legible
    @Override
    public String toString() {
        return "Cliente[id=" + idCliente + ", nombre=" + nombres + " " + apellidos + "]";
    }
}
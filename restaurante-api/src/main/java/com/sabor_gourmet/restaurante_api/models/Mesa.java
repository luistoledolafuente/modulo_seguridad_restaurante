package com.sabor_gourmet.restaurante_api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mesa")
@Data
@NoArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMesa; // Mapea a id_mesa

    @Column(nullable = false, unique = true)
    private Integer numero;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false, length = 20)
    private String estado = "DISPONIBLE"; // disponible, ocupada, reservada, mantenimiento

    // Sobrescribimos toString() para que la Bitácora (AOP) sea legible
    @Override
    public String toString() {
        return "Mesa[id=" + idMesa + ", numero=" + numero + ", estado=" + estado + "]";
    }
}
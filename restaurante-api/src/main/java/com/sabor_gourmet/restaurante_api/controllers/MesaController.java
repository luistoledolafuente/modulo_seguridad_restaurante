package com.sabor_gourmet.restaurante_api.controllers;

import com.sabor_gourmet.restaurante_api.models.Mesa;
import com.sabor_gourmet.restaurante_api.repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin(origins = "http://localhost:3000")
public class MesaController {

    @Autowired
    private MesaRepository mesaRepository;

    // GET (Obtener todas)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public List<Mesa> getAllMesas() {
        return mesaRepository.findAll();
    }

    // GET (Obtener por ID)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public ResponseEntity<Mesa> getMesaById(@PathVariable Integer id) {
        return mesaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST (Crear)
    // ¡AOP AUDITARÁ ESTO!
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Solo Admin crea mesas
    public Mesa createMesa(@RequestBody Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    // PUT (Actualizar estado de la mesa)
    // ¡AOP AUDITARÁ ESTO!
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')") // Admin o Mozo pueden ocupar/liberar mesa
    public ResponseEntity<Mesa> updateMesa(@PathVariable Integer id, @RequestBody Mesa mesaDetails) {
        return mesaRepository.findById(id)
                .map(mesa -> {
                    mesa.setNumero(mesaDetails.getNumero());
                    mesa.setCapacidad(mesaDetails.getCapacidad());
                    mesa.setEstado(mesaDetails.getEstado());
                    return ResponseEntity.ok(mesaRepository.save(mesa));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
package com.sabor_gourmet.restaurante_api.controllers;

import com.sabor_gourmet.restaurante_api.models.Cliente;
import com.sabor_gourmet.restaurante_api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Para seguridad por método
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:3000")
// Habilitamos la seguridad por método (requiere @EnableMethodSecurity en SecurityConfig si no funciona)
// Pero por ahora, confiamos en las reglas de SecurityConfig.
// Vamos a asumir que 'MOZO' o 'ADMIN' pueden manejar clientes.
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // GET (Obtener todos)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    // GET (Obtener por ID)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST (Crear)
    // ¡AOP AUDITARÁ ESTO AUTOMÁTICAMENTE!
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // PUT (Actualizar)
    // ¡AOP AUDITARÁ ESTO AUTOMÁTICAMENTE!
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO')")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente clienteDetails) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombres(clienteDetails.getNombres());
                    cliente.setApellidos(clienteDetails.getApellidos());
                    cliente.setDni(clienteDetails.getDni());
                    cliente.setTelefono(clienteDetails.getTelefono());
                    cliente.setCorreo(clienteDetails.getCorreo());
                    cliente.setEstado(clienteDetails.getEstado());
                    return ResponseEntity.ok(clienteRepository.save(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE (Borrar)
    // ¡AOP AUDITARÁ ESTO AUTOMÁTICAMENTE!
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Solo Admin puede borrar
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
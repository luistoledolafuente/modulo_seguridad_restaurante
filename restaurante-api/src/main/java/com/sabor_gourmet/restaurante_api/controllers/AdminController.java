package com.sabor_gourmet.restaurante_api.controllers;

import com.sabor_gourmet.restaurante_api.dto.UserDTO;
import com.sabor_gourmet.restaurante_api.dto.UserRegistrationRequest;
import com.sabor_gourmet.restaurante_api.models.Rol;
import com.sabor_gourmet.restaurante_api.models.Usuario;
import com.sabor_gourmet.restaurante_api.repositories.RolRepository;
import com.sabor_gourmet.restaurante_api.repositories.UsuarioRepository;
// No necesitas importar Autowired aquí
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // --- Inyección por Constructor (Forma Moderna) ---
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder encoder;

    // Eliminamos @Autowired del constructor.
    // Spring sabe automáticamente cómo inyectar esto.
    public AdminController(UsuarioRepository usuarioRepository,
                           RolRepository rolRepository,
                           PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.encoder = encoder;
    }
    // --- Fin de la corrección ---


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Esta línea es correcta, ignora la advertencia amarilla
        List<UserDTO> userDTOs = usuarios.stream()
                .map(usuario -> new UserDTO(usuario))
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createNewUser(@RequestBody UserRegistrationRequest registerRequest) {

        if (usuarioRepository.findByNombreUsuario(registerRequest.getNombreUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Nombre de usuario ya existe!");
        }

        Optional<Rol> rolOpt = rolRepository.findByNombreRol(registerRequest.getNombreRol());
        Rol rol;
        if (rolOpt.isEmpty()) {
            rol = rolRepository.save(new Rol(registerRequest.getNombreRol()));
        } else {
            rol = rolOpt.get();
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(registerRequest.getNombreUsuario());
        nuevoUsuario.setContrasena(encoder.encode(registerRequest.getContrasena()));
        nuevoUsuario.setRol(rol);

        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario " + nuevoUsuario.getNombreUsuario() + " registrado exitosamente!");
    }
}
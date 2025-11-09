package com.sabor_gourmet.restaurante_api.controllers;

import com.sabor_gourmet.restaurante_api.config.jwt.JwtUtils;
import com.sabor_gourmet.restaurante_api.dto.JwtResponse;
import com.sabor_gourmet.restaurante_api.dto.LoginRequest;
import com.sabor_gourmet.restaurante_api.models.Usuario;
import com.sabor_gourmet.restaurante_api.repositories.RolRepository;
import com.sabor_gourmet.restaurante_api.repositories.UsuarioRepository;
import com.sabor_gourmet.restaurante_api.models.Rol;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // --- Inyección por Constructor (Forma Moderna) ---
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder encoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils,
                          UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.encoder = encoder;
    }
    // --- Fin de la corrección ---


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNombreUsuario(), loginRequest.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario userDetails = (Usuario) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getUsername(),
                userDetails.getRol().getNombreRol()
        ));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody LoginRequest registerRequest) {


        if (usuarioRepository.findByNombreUsuario(registerRequest.getNombreUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Nombre de usuario ya existe!");
        }

        Optional<Rol> rolAdminOpt = rolRepository.findByNombreRol("ROLE_ADMIN");
        if (rolAdminOpt.isEmpty()) {
            rolRepository.save(new Rol("ROLE_ADMIN"));
            rolAdminOpt = rolRepository.findByNombreRol("ROLE_ADMIN");
        }
        Rol rolAdmin = rolAdminOpt.get();

        Usuario admin = new Usuario();
        admin.setNombreUsuario(registerRequest.getNombreUsuario());
        admin.setContrasena(encoder.encode(registerRequest.getContrasena()));
        admin.setRol(rolAdmin);

        usuarioRepository.save(admin);

        return ResponseEntity.ok("Usuario administrador registrado exitosamente!");
    }
}
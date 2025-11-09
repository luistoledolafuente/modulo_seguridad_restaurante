package com.sabor_gourmet.restaurante_api.config.security;

import com.sabor_gourmet.restaurante_api.config.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1. Deshabilitar CSRF (Crucial para API REST)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Definir las reglas de autorización (LIMPIO Y SIN ERRORES)
                .authorizeHttpRequests(auth -> auth
                        // Acceso público (LOGIN y registro)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Restricciones por roles (basadas en el documento original)
                        .requestMatchers("/api/admin/**", "/api/inventario/**").hasRole("ADMIN")
                        .requestMatchers("/api/pedidos/**").hasAnyRole("MOZO", "COCINERO")
                        .requestMatchers("/api/ventas/**").hasAnyRole("CAJERO", "ADMIN")

                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )

                // 3. Configurar como STATELESS (Sin sesiones, solo JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Usar nuestro AuthenticationProvider personalizado
                .authenticationProvider(authenticationProvider)

                // 5. Agregar nuestro filtro JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // 6. Configuración de CORS
                .cors(Customizer.withDefaults());

        return http.build();
    }
}
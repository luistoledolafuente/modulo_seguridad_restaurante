package com.sabor_gourmet.restaurante_api.aspect; // Paquete corregido

import com.sabor_gourmet.restaurante_api.models.Bitacora;
import com.sabor_gourmet.restaurante_api.models.Usuario;
import com.sabor_gourmet.restaurante_api.repositories.BitacoraRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    // --- PUNTO DE CORTE CORREGIDO ---
    // Ahora apunta a tu paquete 'restaurante_api.repositories'
    @Pointcut("execution(* com.sabor_gourmet.restaurante_api.repositories.*.save(..)) || " +
            "execution(* com.sabor_gourmet.restaurante_api.repositories.*.delete(..))")
    public void repositoryOperations() {}

    @AfterReturning(pointcut = "repositoryOperations()", returning = "result")
    public void logAfterOperation(JoinPoint joinPoint, Object result) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return;
        }

        Usuario usuario = null;
        try {
            usuario = (Usuario) authentication.getPrincipal();
        } catch (Exception e) {
            return;
        }

        String methodName = joinPoint.getSignature().getName();
        Object entity = joinPoint.getArgs().length > 0 ? joinPoint.getArgs()[0] : result;

        // Evitar bucle infinito: No auditar cuando guardamos la propia Bitacora
        if (entity instanceof Bitacora) {
            return;
        }

        String accion = String.format("%s %s: %s",
                methodName.toUpperCase(),
                entity.getClass().getSimpleName(),
                entity.toString());

        Bitacora bitacora = new Bitacora(usuario, accion);
        bitacoraRepository.save(bitacora);
    }
}
package com.minimarket.controller;

import com.minimarket.security.model.LoginRequest;
import com.minimarket.security.model.LoginResponse;
import com.minimarket.security.service.CustomUserDetailsService;
import com.minimarket.security.util.JwtUtil;
import com.minimarket.service.ActividadSeguridadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final ActividadSeguridadService actividadSeguridadService;

    public AuthController(AuthenticationConfiguration authenticationConfiguration,
                          JwtUtil jwtUtil,
                          CustomUserDetailsService customUserDetailsService,
                          ActividadSeguridadService actividadSeguridadService) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.actividadSeguridadService = actividadSeguridadService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpServletRequest request) {

        String ipOrigen = request.getRemoteAddr();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(
                    loginRequest.getUsername()
            );

            String token = jwtUtil.generarToken(userDetails);

            actividadSeguridadService.registrar(
                    loginRequest.getUsername(),
                    "LOGIN_EXITOSO",
                    "Inicio de sesión correcto",
                    ipOrigen
            );

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception e) {
            actividadSeguridadService.registrar(
                    loginRequest.getUsername(),
                    "LOGIN_FALLIDO",
                    "Intento de inicio de sesión con credenciales inválidas",
                    ipOrigen
            );

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos");
        }
    }
}
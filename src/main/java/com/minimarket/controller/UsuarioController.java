package com.minimarket.controller;

import com.minimarket.entity.Usuario;
import com.minimarket.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        if (idInvalido(id)) {
            return ResponseEntity.badRequest().body("El id del usuario debe ser válido");
        }

        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario) {
        if (usuario == null) {
            return ResponseEntity.badRequest().body("El usuario no puede ser nulo");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (idInvalido(id)) {
            return ResponseEntity.badRequest().body("El id del usuario debe ser válido");
        }

        if (usuario == null) {
            return ResponseEntity.badRequest().body("El usuario no puede ser nulo");
        }

        Optional<Usuario> usuarioExistente = usuarioService.findById(id);
        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuario.setId(id);
        return ResponseEntity.ok(usuarioService.save(usuario));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (idInvalido(id)) {
            return ResponseEntity.badRequest().body("El id del usuario debe ser válido");
        }

        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean idInvalido(Long id) {
        return id == null || id <= 0;
    }
}
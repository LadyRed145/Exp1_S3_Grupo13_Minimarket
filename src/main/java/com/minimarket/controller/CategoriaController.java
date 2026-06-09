package com.minimarket.controller;

import com.minimarket.entity.Categoria;
import com.minimarket.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'EMPLEADO', 'ADMINISTRADOR')")
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'EMPLEADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> obtenerCategoriaPorId(@PathVariable Long id) {
        if (idInvalido(id)) {
            return ResponseEntity.badRequest().body("El id de la categoría debe ser válido");
        }

        Categoria categoria = categoriaService.findById(id);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> guardarCategoria(@RequestBody Categoria categoria) {
        if (categoria == null) {
            return ResponseEntity.badRequest().body("La categoría no puede ser nula");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoria));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        if (idInvalido(id)) {
            return ResponseEntity.badRequest().body("El id de la categoría debe ser válido");
        }

        if (categoria == null) {
            return ResponseEntity.badRequest().body("La categoría no puede ser nula");
        }

        Categoria existente = categoriaService.findById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        categoria.setId(id);
        return ResponseEntity.ok(categoriaService.save(categoria));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        if (idInvalido(id)) {
            return ResponseEntity.badRequest().body("El id de la categoría debe ser válido");
        }

        Categoria categoria = categoriaService.findById(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean idInvalido(Long id) {
        return id == null || id <= 0;
    }
}
package com.minimarket.controller;

import com.minimarket.entity.Venta;
import com.minimarket.service.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLEADO', 'ADMINISTRADOR')")
    public ResponseEntity<List<Venta>> listarVentas() {
        return ResponseEntity.ok(ventaService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'EMPLEADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> obtenerVentaPorId(@PathVariable Long id) {
        if (idInvalido(id)) {
            return ResponseEntity.badRequest().body("El id de la venta debe ser válido");
        }

        Venta venta = ventaService.findById(id);
        return venta != null ? ResponseEntity.ok(venta) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'EMPLEADO', 'ADMINISTRADOR')")
    public ResponseEntity<?> guardarVenta(@RequestBody Venta venta) {
        if (venta == null) {
            return ResponseEntity.badRequest().body("La venta no puede ser nula");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.save(venta));
    }

    private boolean idInvalido(Long id) {
        return id == null || id <= 0;
    }
}
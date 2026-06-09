package com.minimarket.service.impl;

import com.minimarket.entity.Venta;
import com.minimarket.repository.VentaRepository;
import com.minimarket.service.VentaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;

    public VentaServiceImpl(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta findById(Long id) {
        return ventaRepository.findById(Objects.requireNonNull(id, "El id de la venta no puede ser nulo"))
                .orElse(null);
    }

    @Override
    public Venta save(Venta venta) {
        return ventaRepository.save(Objects.requireNonNull(venta, "La venta no puede ser nula"));
    }

    @Override
    public List<Venta> findByUsuarioId(Long usuarioId) {
        return ventaRepository.findByUsuarioId(Objects.requireNonNull(usuarioId, "El id del usuario no puede ser nulo"));
    }
}
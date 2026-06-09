package com.minimarket.service.impl;

import com.minimarket.entity.Carrito;
import com.minimarket.repository.CarritoRepository;
import com.minimarket.service.CarritoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;

    public CarritoServiceImpl(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    @Override
    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }

    @Override
    public Carrito findById(Long id) {
        return carritoRepository.findById(Objects.requireNonNull(id, "El id del carrito no puede ser nulo"))
                .orElse(null);
    }

    @Override
    public Carrito save(Carrito carrito) {
        return carritoRepository.save(Objects.requireNonNull(carrito, "El carrito no puede ser nulo"));
    }

    @Override
    public void deleteById(Long id) {
        carritoRepository.deleteById(Objects.requireNonNull(id, "El id del carrito no puede ser nulo"));
    }

    @Override
    public List<Carrito> findByUsuarioId(Long usuarioId) {
        return carritoRepository.findByUsuarioId(Objects.requireNonNull(usuarioId, "El id del usuario no puede ser nulo"));
    }
}
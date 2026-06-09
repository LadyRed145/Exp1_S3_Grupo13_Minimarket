package com.minimarket.service.impl;

import com.minimarket.entity.Usuario;
import com.minimarket.repository.UsuarioRepository;
import com.minimarket.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(Objects.requireNonNull(id, "El id del usuario no puede ser nulo"));
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(Objects.requireNonNull(username, "El username no puede ser nulo"));
    }

    @Override
    public Usuario save(Usuario usuario) {
        Usuario usuarioSeguro = Objects.requireNonNull(usuario, "El usuario no puede ser nulo");

        if (usuarioSeguro.getPassword() != null && !usuarioSeguro.getPassword().isBlank()) {
            if (!usuarioSeguro.getPassword().startsWith("$2a$")
                    && !usuarioSeguro.getPassword().startsWith("$2b$")
                    && !usuarioSeguro.getPassword().startsWith("$2y$")) {
                usuarioSeguro.setPassword(passwordEncoder.encode(usuarioSeguro.getPassword()));
            }
        }

        return usuarioRepository.save(usuarioSeguro);
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(Objects.requireNonNull(id, "El id del usuario no puede ser nulo"));
    }
}
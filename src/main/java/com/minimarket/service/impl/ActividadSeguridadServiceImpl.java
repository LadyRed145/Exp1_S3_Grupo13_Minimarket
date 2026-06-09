package com.minimarket.service.impl;

import com.minimarket.entity.ActividadSeguridad;
import com.minimarket.repository.ActividadSeguridadRepository;
import com.minimarket.service.ActividadSeguridadService;
import org.springframework.stereotype.Service;

@Service
public class ActividadSeguridadServiceImpl implements ActividadSeguridadService {

    private final ActividadSeguridadRepository actividadSeguridadRepository;

    public ActividadSeguridadServiceImpl(ActividadSeguridadRepository actividadSeguridadRepository) {
        this.actividadSeguridadRepository = actividadSeguridadRepository;
    }

    @Override
    public void registrar(String username, String tipoEvento, String descripcion, String ipOrigen) {
        ActividadSeguridad actividad = new ActividadSeguridad(
                username,
                tipoEvento,
                descripcion,
                ipOrigen
        );

        actividadSeguridadRepository.save(actividad);
    }
}

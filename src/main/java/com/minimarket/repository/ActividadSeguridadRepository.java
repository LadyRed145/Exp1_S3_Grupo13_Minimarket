package com.minimarket.repository;

import com.minimarket.entity.ActividadSeguridad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadSeguridadRepository extends JpaRepository<ActividadSeguridad, Long> {
}

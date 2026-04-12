package com.uade.tpo.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByEstadoAndFechaUltimaActividadBefore(String estado, LocalDateTime limite);
}

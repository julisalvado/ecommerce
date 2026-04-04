package com.uade.tpo.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import com.uade.tpo.demo.entity.Descuento;

import java.util.List;


@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {
    @Query("select d from Descuento d where d.porcentaje = ?1")
    List<Descuento> findByPorcentaje(double porcentaje);
    
}

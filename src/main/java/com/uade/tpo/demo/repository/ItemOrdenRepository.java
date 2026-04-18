package com.uade.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.ItemOrden;

@Repository
public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {

    // Obtener todos los items de una orden
    @Query("SELECT i FROM ItemOrden i WHERE i.orden.idOrden = ?1")
    List<ItemOrden> findByOrdenId(Long idOrden);

    @Query("SELECT i FROM ItemOrden i WHERE i.idLibro = ?1")
    List<ItemOrden> findByIdLibro(Long idLibro);

    // Calcular total de una orden
    @Query("SELECT SUM(i.subtotal) FROM ItemOrden i WHERE i.orden.idOrden = ?1")
    Float getTotalByOrden(Long idOrden);
}
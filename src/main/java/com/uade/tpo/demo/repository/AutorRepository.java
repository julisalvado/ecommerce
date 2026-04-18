package com.uade.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    List<Autor> findByNombre(String nombre);

}

package com.uade.tpo.demo.service;

//import com.uade.tpo.demo.entity.ImagenLibro;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;

public interface ImagenLibroService {
    //ImagenLibro subirImagen(String nombre, byte[] imagen);
    void eliminarImagen(Long id) throws RecursoNotFoundException;
}
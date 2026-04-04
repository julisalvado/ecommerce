package com.uade.tpo.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.ImagenLibro;
import com.uade.tpo.demo.exceptions.*;
import com.uade.tpo.demo.repository.ImagenLibroRepository;

@Service
public class ImagenLibroServiceImpl implements ImagenLibroService {

    @Autowired
    private ImagenLibroRepository imagenLibroRepository;

    /*public ImagenLibro subirImagen(String nombre, byte[] imagen) {
        ImagenLibro nuevaImagen = new ImagenLibro();
        nuevaImagen.setNombre(nombre);
        nuevaImagen.setImagen(imagen);
        return imagenLibroRepository.save(nuevaImagen);
    }*/

    public void eliminarImagen(Long id) throws RecursoNotFoundException {
        Optional<ImagenLibro> result = imagenLibroRepository.findById(id);
        if (result.isEmpty())
            throw new RecursoNotFoundException();
        imagenLibroRepository.delete(result.get());
    }
}
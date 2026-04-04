package com.uade.tpo.demo.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.demo.entity.ImagenLibro;
import com.uade.tpo.demo.entity.dto.ImagenLibroRequest;
import com.uade.tpo.demo.exceptions.*;
import com.uade.tpo.demo.service.ImagenLibroService;

@RestController
@RequestMapping("imagenes")
public class ImagenLibroController {

    @Autowired
    private ImagenLibroService imagenLibroService;

    /*@PostMapping
    public ResponseEntity<Object> subirImagen(@RequestBody ImagenLibroRequest request) {
        ImagenLibro result = imagenLibroService.subirImagen(
            request.getNombre(), 
            request.getImagen()
        );
        return ResponseEntity.created(URI.create("/imagenes/" + result.getId())).body(result);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarImagen(@PathVariable Long id)
            throws RecursoNotFoundException {
        imagenLibroService.eliminarImagen(id);
        return ResponseEntity.noContent().build();
    }
}

package com.uade.tpo.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.dto.LibroRequest;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.LibroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    // GET /libros
    @GetMapping
    public List<Libro> getLibros(
            @RequestParam(required = false) Long genero,
            @RequestParam(required = false) Float precioMin,
            @RequestParam(required = false) Float precioMax,
            @RequestParam(required = false) Long autor
    ) throws RecursoNotFoundException {

        // Filtro
        if (genero != null) {
            return libroService.getLibrosByGenero(genero);
        }

        if (precioMin != null && precioMax != null) {
            return libroService.getLibrosByPrecio(precioMin, precioMax);
        }

        if (autor != null) {
            return libroService.getLibrosByAutor(autor);
        }

        // Sin filtros
        return libroService.getLibros();
    }

    // GET /libros/{id}
    @GetMapping("/{id}")
    public Libro getLibroById(@PathVariable Long id) throws RecursoNotFoundException {
        return libroService.getLibroById(id);
    }

    // POST /libros
    @PostMapping
    public Libro createLibro(@RequestBody LibroRequest request) throws RecursoNotFoundException {
        return libroService.createLibro(request);
    }
}

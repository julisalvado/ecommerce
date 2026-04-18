package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.Autor;
import com.uade.tpo.demo.entity.Descuento;
import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.entity.Genero;
import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.LibroRequest;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.AutorRepository;
import com.uade.tpo.demo.repository.DescuentoRepository;
import com.uade.tpo.demo.repository.EditorialRepository;
import com.uade.tpo.demo.repository.GeneroRepository;
import com.uade.tpo.demo.repository.LibroRepository;
import com.uade.tpo.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final GeneroRepository generoRepository;
    private final EditorialRepository editorialRepository;
    private final DescuentoRepository descuentoRepository;
    private final UserRepository userRepository;
    private final AutorRepository autorRepository;

    @Override
    public List<Libro> getLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro getLibroById(Long id){
        Optional<Libro> libroOpt = libroRepository.findById(id);

        if (libroOpt.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El libro seleccionado no existe");
        }

        return libroOpt.get();
    }

    @Override
    public Libro createLibro(LibroRequest request) throws RecursoNotFoundException {

        if (libroRepository.existsByTitulo(request.getTitulo())) {
            throw new RuntimeException("El libro ya existe");
        }

        Libro libro = new Libro();

        libro.setTitulo(request.getTitulo());
        libro.setPrecio(request.getPrecio());
        libro.setStock(request.getStock());

        // Genero
        Optional<Genero> generoOpt = generoRepository.findById(request.getIdGenero());
        if (generoOpt.isEmpty()) {
            throw new RecursoNotFoundException();
        }

        // Editorial
        Optional<Editorial> editorialOpt = editorialRepository.findById(request.getIdEditorial());
        if (editorialOpt.isEmpty()) {
            throw new RecursoNotFoundException();
        }

        // Vendedor
        Optional<User> userOpt = userRepository.findById(request.getIdVendedor());
        if (userOpt.isEmpty()) {
            throw new RecursoNotFoundException();
        }

        libro.setGenero(generoOpt.get());
        libro.setEditorial(editorialOpt.get());
        libro.setVendedor(userOpt.get());

        // Descuento
        if (request.getIdDescuento() != null) {
            Optional<Descuento> descuentoOpt = descuentoRepository.findById(request.getIdDescuento());

            if (descuentoOpt.isEmpty()) {
                throw new RecursoNotFoundException();
            }

            libro.setDescuento(descuentoOpt.get());
        }

        return libroRepository.save(libro);
    }

    @Override
    public List<Libro> getLibrosByGenero(Long idGenero) throws RecursoNotFoundException {

        Optional<Genero> generoOpt = generoRepository.findById(idGenero);

        if (generoOpt.isEmpty()) {
            throw new RecursoNotFoundException();
        }

        return libroRepository.findByGenero(generoOpt.get());
    }

    @Override
    public List<Libro> getLibrosByPrecio(float precioMin, float precioMax) {
        return libroRepository.findByPrecioBetween(precioMin, precioMax);
    }

    @Override
    public List<Libro> getLibrosByAutor(Long idAutor) throws RecursoNotFoundException {

        Optional<Autor> autorOpt = autorRepository.findById(idAutor);

        if (autorOpt.isEmpty()) {
            throw new RecursoNotFoundException();
        }

        return libroRepository.findByAutor(autorOpt.get());
    }

    public boolean tieneStock(Long id, int cantidadSolicitada) {
        return libroRepository.findById(id)
            .map(libro -> libro.getStock() >= cantidadSolicitada) 
            .orElse(false); 
    }

    public void descontarStock(Long id, int cantidad) {
     
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));

    
        if (libro.getStock() < cantidad) {
            throw new RuntimeException("No hay stock suficiente para el libro: " + libro.getTitulo());
        }

      
        int nuevoStock = libro.getStock() - cantidad;
        libro.setStock(nuevoStock);

        libroRepository.save(libro);
    }
}

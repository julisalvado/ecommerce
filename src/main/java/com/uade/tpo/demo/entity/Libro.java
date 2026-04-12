package com.uade.tpo.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Libro {

    public Libro() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    private String titulo;

    private float precio;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "idGenero", nullable = false)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "idEditorial", nullable = false)
    private Editorial editorial;

    @ManyToOne
    @JoinColumn(name = "idDescuento")
    private Descuento descuento;

    @ManyToOne
    @JoinColumn(name = "idVendedor", nullable = false)
    private User vendedor;

    @ManyToOne
    @JoinColumn(name = "idAutor", nullable = false)
    private Autor autor;

    public Long getId() {
        return idLibro;
    }
}

package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Editorial {
    public Editorial() {}

    public Editorial(String nombre) {
        this.nombre = nombre;
       
    }


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    /*@OneToMany(mappedBy = "editorial")
    private List<Libro> libros;*/

}

package com.uade.tpo.demo.entity;

//import javax.persistence.JoinColumns;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
//import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ImagenLibro {
    public ImagenLibro() {}

    public ImagenLibro(String nombre) {
        this.nombre = nombre;
    }


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;
    
    @Lob  // así se mapea un blob en JPA
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    

    /*@ManyToOne
    JoinColumns(name = "libro_id")
    private Libro libro;*/

}

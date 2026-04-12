package com.uade.tpo.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Carrito {

    public Carrito() {}

    public Carrito(Long idUsuario, LocalDateTime fechaUltimaActividad, String estado, float total) {
        this.idUsuario = idUsuario;
        this.fechaUltimaActividad = fechaUltimaActividad;
        this.estado = estado;
        this.total = total;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;

    @Column
    private Long idUsuario;

    @Column
    private LocalDateTime fechaUltimaActividad;

    @Column
    private String estado;

    @Column
    private float total;

    @OneToMany(mappedBy = "carrito")
    private List<ItemCarrito> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idUsuario", insertable = false, updatable = false)
    private User usuario;
}

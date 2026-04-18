package com.uade.tpo.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;


@Data
@Entity
public class Orden {

    public Orden() {
    }

    public Orden(User usuario, LocalDateTime fechaVenta, Float total, String estado, Long idCarrito, String metodoPago) {
        this.usuario = usuario;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.carrito = carrito;
        this.metodoPago = metodoPago;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrden;

    private LocalDateTime fechaVenta;
    private Float total;

    @Column
    private String estado;
    //private Long idCarrito;
    @Column
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "idUsuario") 
    private User usuario;

    @OneToMany(mappedBy = "orden", orphanRemoval = true)
    private List<ItemOrden> items = new ArrayList<>();
}
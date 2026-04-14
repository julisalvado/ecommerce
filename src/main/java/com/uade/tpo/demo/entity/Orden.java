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
        this.idCarrito = idCarrito;
        this.metodoPago = metodoPago;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrden;

    private LocalDateTime fechaVenta;
    private Float total;
    private String estado;
    private Long idCarrito;
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "idUsuario") 
    private User usuario;

    @OneToMany(mappedBy = "orden", orphanRemoval = true)
    private List<ItemOrden> items = new ArrayList<>();
}
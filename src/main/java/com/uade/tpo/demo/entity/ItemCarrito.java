package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ItemCarrito {

    public ItemCarrito () {}

    public ItemCarrito(Long idCarrito, Long idLibro, int cantidad, float precioUnitario, float subtotal) {
        this.idCarrito = idCarrito;
        this.idLibro = idLibro;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemCarrito;

    @Column
    private Long idCarrito;

    @Column
    private Long idLibro;

    @Column
    private int cantidad;

    @Column
    private float precioUnitario;

    @Column
    private float subtotal;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    public float calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
        return this.subtotal;
    }
}

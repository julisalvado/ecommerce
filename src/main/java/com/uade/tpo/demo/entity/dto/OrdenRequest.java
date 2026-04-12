package com.uade.tpo.demo.entity.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrdenRequest {
    private Long idUsuario;
    private LocalDateTime fechaVenta;
    private Float total;
    private String estado;
    private Long idCarrito;
    private String metodoPago;
}
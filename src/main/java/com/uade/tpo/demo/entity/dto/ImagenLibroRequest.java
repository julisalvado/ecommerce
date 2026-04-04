package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class ImagenLibroRequest {
    private String nombre;
    private byte[] imagen;
}
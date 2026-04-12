package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.OrdenRequest;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface OrdenService {

    public Orden createOrden(OrdenRequest request);

    public Orden crearDesdeCarrito(Carrito carrito, List<ItemCarrito> items);
}
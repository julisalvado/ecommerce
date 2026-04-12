package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.ItemOrden;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.OrdenRequest;
import com.uade.tpo.demo.repository.OrdenRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    public Orden createOrden(OrdenRequest request) {
        Orden orden = new Orden(
                request.getIdUsuario(),
                request.getFechaVenta(),
                request.getTotal(),
                request.getEstado(),
                request.getIdCarrito(),
                request.getMetodoPago()
        );

        return ordenRepository.save(orden);
    }

    @Override
    @Transactional
    public Orden crearDesdeCarrito(Carrito carrito, List<ItemCarrito> items) {
        // 1. Creamos la cabecera de la Orden
        Orden nuevaOrden = new Orden();
        nuevaOrden.setUsuario(carrito.getUsuario()); // O idUsuario según tu entidad
        nuevaOrden.setFechaVenta(LocalDateTime.now());
        nuevaOrden.setEstado("PENDIENTE_PAGO");
        
        // 2. Transformamos los ítems del carrito a ítems de la orden
        List<ItemOrden> itemsOrden = items.stream().map(itemCarrito -> {
            ItemOrden itemOrden = new ItemOrden();
            itemOrden.setIdLibro(itemCarrito.getIdLibro());
            itemOrden.setCantidad(itemCarrito.getCantidad());
            itemOrden.setPrecioUnitario(itemCarrito.getPrecioUnitario()); // Importante
            itemOrden.setSubtotal(itemCarrito.getSubtotal());
            itemOrden.setOrden(nuevaOrden); // Vinculamos a la orden madre
            return itemOrden;
        }).collect(Collectors.toList());

        // 3. Asignamos la lista y calculamos el total general
        nuevaOrden.setItems(itemsOrden);
        float total = (float) itemsOrden.stream()
                .mapToDouble(ItemOrden::getSubtotal)
                .sum();
        nuevaOrden.setTotal(total);

        // 4. Guardamos todo (al tener CascadeType.ALL en Orden, guarda los items también)
        return ordenRepository.save(nuevaOrden);
    }
}
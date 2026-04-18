package com.uade.tpo.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.ItemOrden;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.OrdenRequest;
import com.uade.tpo.demo.repository.OrdenRepository;
import com.uade.tpo.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public Orden createOrden(OrdenRequest request) {
        User user = userRepository.findById(request.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Orden orden = new Orden(
                user,
                request.getFechaVenta(),
                request.getTotal(),
                request.getEstado(),
                request.getIdCarrito(),
                request.getMetodoPago()
        );

    //@Autowired
    //private LibroService libroService;

    // Obtener detalle de una orden con sus items embedidos
    @Override
    public List<OrdenResponse> getByUsuario(Long idUsuario) {
    return ordenRepository.findByUsuarioIdUsuario(idUsuario).stream()
            .map(orden -> {
                OrdenResponse response = new OrdenResponse();
                response.setIdOrden(orden.getIdOrden());
                response.setIdUsuario(orden.getUsuario().getIdUsuario());
                response.setFechaVenta(orden.getFechaVenta());
                response.setTotal(orden.getTotal());
                response.setEstado(orden.getEstado());
                response.setMetodoPago(orden.getMetodoPago());
                return response;
            }).collect(Collectors.toList());
    }

    @Override
    public OrdenDetalleResponse getById(Long idOrden) {
    Orden orden = ordenRepository.findById(idOrden)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "La orden con id " + idOrden + " no existe"));

    OrdenDetalleResponse response = new OrdenDetalleResponse();
    response.setIdOrden(orden.getIdOrden());
    response.setFechaVenta(orden.getFechaVenta());
    response.setTotal(orden.getTotal());
    response.setEstado(orden.getEstado());
    response.setMetodoPago(orden.getMetodoPago());
    response.setItems(itemOrdenService.getItemsByOrden(idOrden));

    return response;
    }

    

    //todas las ordenes
    @Override
    public List<OrdenResponse> getOrdenes() {
    return ordenRepository.findAll().stream()
            .map(orden -> {
                OrdenResponse response = new OrdenResponse();
                response.setIdOrden(orden.getIdOrden());
                response.setIdUsuario(orden.getUsuario().getIdUsuario());
                response.setFechaVenta(orden.getFechaVenta());
                response.setTotal(orden.getTotal());
                response.setEstado(orden.getEstado());
                response.setMetodoPago(orden.getMetodoPago());
                return response;
            }).collect(Collectors.toList());
    }

    

    // Crear orden desde el checkout — solo llamado por CarritoService
    @Override
    @Transactional
    public Orden crearDesdeCarrito(Carrito carrito, List<ItemCarrito> items) {

        // 1. crear la cabecera de la orden
        Orden nuevaOrden = new Orden();
        nuevaOrden.setUsuario(carrito.getUsuario());
        nuevaOrden.setCarrito(carrito);
        nuevaOrden.setFechaVenta(new Date());
        nuevaOrden.setEstado("CONFIRMADA");

        // 2. transformar items del carrito a items de la orden
        List<ItemOrden> itemsOrden = items.stream().map(itemCarrito -> {
            ItemOrden itemOrden = new ItemOrden();
            itemOrden.setLibro(itemCarrito.getLibro());
            itemOrden.setCantidad(itemCarrito.getCantidad());
            itemOrden.setPrecioUnitario(itemCarrito.getPrecioUnitario());
            itemOrden.calcularSubtotal();
            itemOrden.setOrden(nuevaOrden);
            return itemOrden;
        }).collect(Collectors.toList());

        // 3. asignar items y calcular total
        nuevaOrden.setItems(itemsOrden);
        float total = (float) itemsOrden.stream()
                .mapToDouble(ItemOrden::getSubtotal)
                .sum();
        nuevaOrden.setTotal(total);

        // 4. guardar todo con cascade
        return ordenRepository.save(nuevaOrden);
    }

    @Override
    @Transactional
    public Orden crearDesdeCarrito(Carrito carrito, List<ItemCarrito> items) {
        // Creamos la cabecera de la Orden
        Orden nuevaOrden = new Orden();
        nuevaOrden.setUsuario(carrito.getUsuario()); // Asignamos el usuario de la orden
        nuevaOrden.setFechaVenta(LocalDateTime.now());
        nuevaOrden.setEstado("PENDIENTE_PAGO");
        
        // Transformamos los ítems del carrito a ítems de la orden
        List<ItemOrden> itemsOrden = items.stream().map(itemCarrito -> {
            ItemOrden itemOrden = new ItemOrden();
            itemOrden.setIdLibro(itemCarrito.getIdLibro());
            itemOrden.setCantidad(itemCarrito.getCantidad());
            itemOrden.setPrecioUnitario(itemCarrito.getPrecioUnitario());
            itemOrden.setSubtotal(itemCarrito.getSubtotal());
            itemOrden.setOrden(nuevaOrden); // Vinculamos a la orden madre
            return itemOrden;
        }).collect(Collectors.toList());

        //Asignamos la lista y calculamos el total general
        nuevaOrden.setItems(itemsOrden);
        float total = (float) itemsOrden.stream()
                .mapToDouble(ItemOrden::getSubtotal)
                .sum();
        nuevaOrden.setTotal(total);

        // Guardamos todo (al tener CascadeType.ALL en Orden, guarda los items también)
        return ordenRepository.save(nuevaOrden);
    }
}
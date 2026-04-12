package com.uade.tpo.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.Libro;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.repository.CarritoRepository;
import com.uade.tpo.demo.repository.ItemCarritoRepository;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private LibroService libroService;

    @Autowired
    private OrdenService ordenService;

    // Obtener carrito activo del usuario
    @Override
    public Carrito getCarritoActivo(Long usuarioId) {
        return carritoRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El carrito para el usuario " + usuarioId + " no existe"));
    }

    // Agregar item al carrito
    @Override
    public ItemCarrito agregarItem(Long usuarioId, Long libroId, int cantidad) {

        Carrito carrito = getCarritoActivo(usuarioId);
        Libro libro = libroService.getLibroById(libroId);

        // verificar stock
        if (libro.getStock() < cantidad)
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El stock para el usuario " + usuarioId + " no existe"); 

        ItemCarrito item = new ItemCarrito();
        item.setIdCarrito(carrito.getIdCarrito());
        item.setIdLibro(libroId);
        item.setCantidad(cantidad);
        item.setPrecioUnitario(libro.getPrecio());
        item.calcularSubtotal();

        // actualizar fecha de ultima actividad
        carrito.setFechaUltimaActividad(LocalDateTime.now());
        carritoRepository.save(carrito);

        return itemCarritoRepository.save(item);
    }

    // Modificar cantidad de un item
    @Override
    public ItemCarrito modificarItem(Long carritoId, Long itemId, int cantidad) {

        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El item " + itemId + " no existe"));

        Libro libro = libroService.getLibroById(item.getIdLibro());

        if (libro.getStock() < cantidad)
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El stock para el libro " + libro.getTitulo() + " no existe"); 

        item.setCantidad(cantidad);
        item.calcularSubtotal();

        // actualizar fecha de ultima actividad
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El carrito "+ carritoId + " no existe"));
        carrito.setFechaUltimaActividad(LocalDateTime.now());
        carritoRepository.save(carrito);

        return itemCarritoRepository.save(item);
    }

    // Eliminar item del carrito
    @Override
    public void eliminarItem(Long carritoId, Long itemId) {
        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El item solicitado no existe"));
        itemCarritoRepository.delete(item);
    }

    // Checkout — lógica principal
    @Override
    @Transactional
    public Orden checkout(Long usuarioId) {

        // 1. obtener carrito activo
        Carrito carrito = getCarritoActivo(usuarioId);

        // 2. obtener items
        List<ItemCarrito> items = itemCarritoRepository.findByCarritoId(carrito.getIdCarrito());

        if (items.isEmpty())
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El carrito para el usuario " + usuarioId + " está vacío"); // carrito vacío

        // 3. verificar stock de TODOS los items antes de descontar cualquiera
        for (ItemCarrito item : items) {
            Libro libro = libroService.getLibroById(item.getIdLibro());
            if (!libroService.tieneStock(item.getIdLibro(), item.getCantidad()))
                throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El libro " + libro.getTitulo() + " no tiene stock");
        }

        // 4. descontar stock
        for (ItemCarrito item : items) {
            libroService.descontarStock(item.getIdLibro(), item.getCantidad());
        }

        // 5. crear la orden con sus items
        Orden orden = ordenService.crearDesdeCarrito(carrito, items);

        // 6. vaciar el carrito
        vaciarCarrito(carrito.getIdCarrito());

        return orden;
    }

    // Vaciar items del carrito (mantiene el carrito activo)
    @Override
    public void vaciarCarrito(Long carritoId) {
        itemCarritoRepository.deleteByCarritoId(carritoId);
    }

    // Marcar carrito como abandonado
    @Override
    public void marcarAbandonado(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "El carrito " + carritoId + " no existe"));
        carrito.setEstado("ABANDONADO");
        carritoRepository.save(carrito);
    }

    // Llamado por el job cada 1 hora
    @Override
    public void vaciarCarritosVencidos() {
        LocalDateTime limite = LocalDateTime.now().minusWeeks(1);
        List<Carrito> vencidos = carritoRepository.findByEstadoAndFechaUltimaActividadBefore("ACTIVO", limite);
        for (Carrito carrito : vencidos) {
            vaciarCarrito(carrito.getIdCarrito());
            carrito.setEstado("ABANDONADO");
            carritoRepository.save(carrito);
        }
    }
}
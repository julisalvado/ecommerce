package com.uade.tpo.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.Orden;
import com.uade.tpo.demo.entity.dto.ItemCarritoRequest;
import com.uade.tpo.demo.exceptions.RecursoNotFoundException;
import com.uade.tpo.demo.service.CarritoService;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // GET /carrito/{usuarioId}
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Carrito> getCarrito(@PathVariable Long usuarioId) throws RecursoNotFoundException {
        Carrito carrito = carritoService.getCarritoActivo(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    // POST /carrito/{usuarioId}/items
    @PostMapping("/{usuarioId}/items")
    public ResponseEntity<ItemCarrito> agregarItem(
            @PathVariable Long usuarioId, 
            @RequestBody ItemCarritoRequest request) throws RecursoNotFoundException {
        
        ItemCarrito nuevoItem = carritoService.agregarItem(
                usuarioId, 
                request.getLibroId(), 
                request.getCantidad()
        );
        return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
    }

    // PUT /carrito/{usuarioId}/items/{itemId}
    @PutMapping("/{usuarioId}/items/{itemId}")
    public ResponseEntity<ItemCarrito> modificarItem(
            @PathVariable Long usuarioId,
            @PathVariable Long itemId,
            @RequestBody int cantidad) throws RecursoNotFoundException {
        
        Carrito carrito = carritoService.getCarritoActivo(usuarioId);
        
        ItemCarrito itemModificado = carritoService.modificarItem(
                carrito.getIdCarrito(), 
                itemId, 
                cantidad
        );
        return ResponseEntity.ok(itemModificado);
    }

    // DELETE /carrito/{usuarioId}/items/{itemId}
    @DeleteMapping("/{usuarioId}/items/{itemId}")
    public ResponseEntity<Void> eliminarItem(
            @PathVariable Long usuarioId, 
            @PathVariable Long itemId) throws RecursoNotFoundException {
        
        Carrito carrito = carritoService.getCarritoActivo(usuarioId);
        carritoService.eliminarItem(carrito.getIdCarrito(), itemId);
        return ResponseEntity.noContent().build();
    }

    // DELETE /carrito/{usuarioId}/items (Vaciar carrito)
    @DeleteMapping("/{usuarioId}/items")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) throws RecursoNotFoundException {
        Carrito carrito = carritoService.getCarritoActivo(usuarioId);
        carritoService.vaciarCarrito(carrito.getIdCarrito());
        return ResponseEntity.noContent().build();
    }

    // POST /carrito/{usuarioId}/checkout
    @PostMapping("/{usuarioId}/checkout")
    public ResponseEntity<Orden> checkout(@PathVariable Long usuarioId) throws RecursoNotFoundException {
        Orden orden = carritoService.checkout(usuarioId);
        return ResponseEntity.ok(orden);
    }
}



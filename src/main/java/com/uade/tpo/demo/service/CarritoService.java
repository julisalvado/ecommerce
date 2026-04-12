package com.uade.tpo.demo.service;



import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.entity.ItemCarrito;
import com.uade.tpo.demo.entity.Orden;

public interface CarritoService {
    public Carrito getCarritoActivo(Long usuarioId);
    public ItemCarrito agregarItem(Long usuarioId, Long libroId, int cantidad);
    public ItemCarrito modificarItem(Long carritoId, Long itemId, int cantidad);
    public void eliminarItem(Long carritoId, Long itemId);
    public Orden checkout(Long usuarioId);
    public void vaciarCarrito(Long carritoId);
    public void marcarAbandonado(Long carritoId);
    public void vaciarCarritosVencidos();
}

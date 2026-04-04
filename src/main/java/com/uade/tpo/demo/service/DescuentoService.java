package com.uade.tpo.demo.service;

//import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.exceptions.*;
import com.uade.tpo.demo.entity.Descuento;
//import java.util.Optional;


public interface DescuentoService {
    public Page<Descuento> getDescuentos(PageRequest pageRequest);
    public Descuento toggleActivo(Long id) throws RecursoNotFoundException;
    public Descuento createDescuento(double porcentaje) throws RecursoDuplicateException;
}

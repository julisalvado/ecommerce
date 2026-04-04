package com.uade.tpo.demo.service;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.uade.tpo.demo.entity.Descuento;
import com.uade.tpo.demo.repository.DescuentoRepository;
import com.uade.tpo.demo.exceptions.*;

@Service
public class DescuentoServiceImpl implements DescuentoService {

    @Autowired
    private DescuentoRepository descuentoRepository;
    
    public Page<Descuento> getDescuentos(PageRequest pageRequest) {
        return descuentoRepository.findAll(pageRequest);
   
    }
    public Descuento createDescuento(double porcentaje) throws RecursoDuplicateException {
        List<Descuento> existentes = descuentoRepository.findByPorcentaje(porcentaje);
        if (!existentes.isEmpty())
            throw new RecursoDuplicateException();
        Descuento nuevo = new Descuento();
        nuevo.setPorcentaje(porcentaje);
        nuevo.setActivo(true); // por defecto activo al crear
        return descuentoRepository.save(nuevo);
    }

    public Descuento toggleActivo(Long id) throws RecursoNotFoundException {
        Optional<Descuento> result = descuentoRepository.findById(id);
        if (result.isEmpty())
            throw new RecursoNotFoundException();
        Descuento descuento = result.get();
        descuento.setActivo(!descuento.isActivo()); // invierte el estado
        return descuentoRepository.save(descuento);
    }
    
}

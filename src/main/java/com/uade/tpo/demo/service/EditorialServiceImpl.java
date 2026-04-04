package com.uade.tpo.demo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.exceptions.*;
import com.uade.tpo.demo.repository.EditorialRepository;

@Service
public class EditorialServiceImpl implements EditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    public Page<Editorial> getEditoriales(PageRequest pageRequest) {
        return editorialRepository.findAll(pageRequest);
    }


    public List<Editorial> getEditorialByNombre(String nombre) throws RecursoNotFoundException {
        List<Editorial> result = editorialRepository.findByNombre(nombre);
        if (result.isEmpty())
            throw new RecursoNotFoundException();
        return result;
    }

    public Editorial createEditorial(String nombre) throws RecursoDuplicateException {
        List<Editorial> existentes = editorialRepository.findByNombre(nombre);
        if (!existentes.isEmpty())
            throw new RecursoDuplicateException();
        return editorialRepository.save(new Editorial(nombre));
    }

    /*public Editorial updateEditorial(Long id, String nombre) throws EditorialNotFoundException {
        Optional<Editorial> result = editorialRepository.findById(id);
        if (result.isEmpty())
            throw new EditorialNotFoundException();
        Editorial editorial = result.get();
        editorial.setNombre(nombre);
        return editorialRepository.save(editorial);
    }*/
}
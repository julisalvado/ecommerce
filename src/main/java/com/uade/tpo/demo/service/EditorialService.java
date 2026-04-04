package com.uade.tpo.demo.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Editorial;
import com.uade.tpo.demo.exceptions.*;


public interface EditorialService {
    Page<Editorial> getEditoriales(PageRequest pageRequest);
    //Optional<Editorial> getEditorialById(Long id);
    List<Editorial> getEditorialByNombre(String nombre)throws RecursoNotFoundException;
    Editorial createEditorial(String nombre) throws RecursoDuplicateException;
}
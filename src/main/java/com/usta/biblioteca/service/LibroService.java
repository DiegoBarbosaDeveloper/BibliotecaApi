package com.usta.biblioteca.service;

import com.usta.biblioteca.domain.Libro;
import com.usta.biblioteca.dto.LibroRequest;
import com.usta.biblioteca.dto.LibroResponse;
import com.usta.biblioteca.mapper.LibroMapper;
import com.usta.biblioteca.repository.LibroRepository;
import com.usta.biblioteca.view.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LibroService {
    // TODO: Implementar reglas de negocio, dependencias y límites transaccionales.
    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;

    public List<LibroResponse> getLibros(){
        return libroRepository.findAll()
                .stream()
                .map(libroMapper::toResponse)
                .toList();
    }

    public LibroResponse getLibro(Long id){
        if(!libroRepository.existsById(id)){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        if(libroRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        return libroMapper.toResponse(libroRepository.findById(id).get());
    }

    public LibroResponse addLibro(LibroRequest libroRequest){
        return null;
    }



}

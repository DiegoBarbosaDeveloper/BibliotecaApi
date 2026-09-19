package com.usta.biblioteca.service;

import com.usta.biblioteca.dto.LibroRequest;
import com.usta.biblioteca.dto.LibroResponse;
import com.usta.biblioteca.mapper.LibroMapper;
import com.usta.biblioteca.repository.LibroRepository;
import com.usta.biblioteca.view.exception.BusinessRuleException;
import com.usta.biblioteca.view.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LibroService {
    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;

    public List<LibroResponse> getLibros(){
        return libroRepository.findAll()
                .stream()
                .map(libroMapper::toResponse)
                .toList();
    }

    public LibroResponse getLibro(@NonNull Long id){
        if(!libroRepository.existsById(id)){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        if(libroRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        return libroMapper.toResponse(libroRepository.findById(id).get());
    }

    public LibroResponse save(@NonNull LibroRequest libroRequest){
        if(libroRepository.existsByIsbn(libroRequest.isbn())){
            throw new BusinessRuleException("Libro existente");
        }

        return  libroMapper.toResponse(
                    libroRepository.save(
                            libroMapper.toEntity(libroRequest)
                    )
        );

    }

    public LibroResponse update(@NonNull Long id, @NonNull LibroRequest libroRequest){
        if(!libroRepository.existsById(id)){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        if (libroRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        if(libroRequest.isbn().isBlank() || libroRequest.titulo().isBlank()
                || libroRequest.autor().isBlank()
        ){
            throw new BusinessRuleException("Las libros no puede ser vacíos");
        }
        var libroToUpdate = libroRepository.findById(id).get();
        libroMapper.update(libroToUpdate, libroRequest);
        libroRepository.save(libroToUpdate);
        return libroMapper.toResponse(libroRepository.findById(id).get());
    }

    public void deleteById(@NonNull Long id){
        if(!libroRepository.existsById(id)){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        libroRepository.deleteById(id);
    }



}

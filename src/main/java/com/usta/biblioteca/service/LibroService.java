package com.usta.biblioteca.service;

import com.usta.biblioteca.domain.EstadoPrestamo;
import com.usta.biblioteca.dto.LibroRequest;
import com.usta.biblioteca.dto.LibroResponse;
import com.usta.biblioteca.mapper.LibroMapper;
import com.usta.biblioteca.repository.LibroRepository;
import com.usta.biblioteca.repository.UsuarioRepository;
import com.usta.biblioteca.view.exception.BusinessRuleException;
import com.usta.biblioteca.view.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LibroService {
    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;
    private final PrestamoService prestamoService;

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

    public List<LibroResponse> getLibrosByTitulo(@NonNull String titulo){
        return libroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(libroMapper::toResponse)
                .toList();
    }

    public List<LibroResponse> getLibrosActivos(Boolean activos){
        return libroRepository.findByDisponible(activos)
                .stream()
                .map(libroMapper::toResponse)
                .toList();
    }

    public List<LibroResponse> getLibrosActivosPorTitulo(@NonNull String titulo, Boolean activos){
        return libroRepository.findByTituloContainingIgnoreCaseAndDisponible(titulo, activos)
                .stream()
                .map(libroMapper::toResponse)
                .toList();
    }

    public LibroResponse save(@NonNull LibroRequest libroRequest){
        if(libroRepository.existsByIsbn(libroRequest.isbn())){
            throw new BusinessRuleException("Libro existente");
        }

        var toCreate = libroMapper.toEntity(libroRequest);
        toCreate.setDisponible(true);

        return  libroMapper.toResponse(libroRepository.save(toCreate));

    }

    public LibroResponse update(@NonNull Long id, @NonNull LibroRequest libroRequest){
        if(!libroRepository.existsById(id)){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        if (libroRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Libro no encontrado");
        }
        if(libroRequest.titulo().isBlank()
                || libroRequest.autor().isBlank()
        ){
            throw new BusinessRuleException("Las libros no puede ser vacíos");
        }
        if(libroRepository.existsByIsbnAndIdNot(libroRequest.isbn(), id)){
            throw new BusinessRuleException("ISBN en uso");
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

        var lista = prestamoService.listar(EstadoPrestamo.DEVUELTO, id);

        if(!lista.isEmpty()){
            throw new BusinessRuleException("El libro contiene prestamos");
        }

        libroRepository.deleteById(id);
    }



}

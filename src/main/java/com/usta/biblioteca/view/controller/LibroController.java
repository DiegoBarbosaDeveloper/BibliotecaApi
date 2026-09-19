package com.usta.biblioteca.view.controller;

import com.usta.biblioteca.dto.LibroRequest;
import com.usta.biblioteca.dto.LibroResponse;
import com.usta.biblioteca.repository.LibroRepository;
import com.usta.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("api/v1/libros")
@RequiredArgsConstructor

public class LibroController {
    // TODO: Agregar endpoints /api/v1, validación de entrada y delegación al servicio.
    private final LibroService libroService;
    private final LibroRepository libroRepository;

    @GetMapping
    public ResponseEntity <List <LibroResponse>> getAllLibros(){
        List<LibroResponse> libros = libroService.getLibros();
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse>getLibroById(@PathVariable Long id){
        LibroResponse libro = libroService.getLibro(id);
        return ResponseEntity.ok(libro);
    }

    @GetMapping()
    public ResponseEntity<List<LibroResponse>> getLibroByTitle(@RequestParam String titulo){
        return ResponseEntity.ok(libroService.getLibrosByTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<LibroResponse> createLibro(@Valid @RequestBody LibroRequest request){
        LibroResponse nuevoLibro = libroService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLibro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponse> updateLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibroRequest request){
        LibroResponse libroActualizado = libroService.update(id, request);
        return ResponseEntity.ok(libroActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id){
        libroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

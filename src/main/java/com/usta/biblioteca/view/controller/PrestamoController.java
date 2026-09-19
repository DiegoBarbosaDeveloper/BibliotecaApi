package com.usta.biblioteca.view.controller;

import com.usta.biblioteca.domain.EstadoPrestamo;
import com.usta.biblioteca.dto.*;
import com.usta.biblioteca.service.PrestamoService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
@RequiredArgsConstructor
public class PrestamoController {
    private final PrestamoService prestamoService;

    @GetMapping
    public List<PrestamoResponse> listar(@RequestParam(required = false) EstadoPrestamo estado,
                                        @RequestParam(required = false) Long libroId) {
        return prestamoService.listar(estado, libroId);
    }

    @GetMapping("/{id}")
    public PrestamoResponse obtener(@PathVariable Long id) { return prestamoService.obtener(id); }

    @PostMapping
    public ResponseEntity<PrestamoResponse> prestar(@Valid @RequestBody PrestamoRequest request) {
        PrestamoResponse creado = prestamoService.prestar(request);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(creado.id()).toUri()).body(creado);
    }
    
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public PrestamoResponse prestar(@Valid @RequestBody PrestamoRequest request, HttpServletResponse response) {
//        PrestamoResponse creado = prestamoService.prestar(request);
//        
//        String location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(creado.id())
//                .toUriString();
//                
//        response.setHeader("Location", location);
//        
//        return creado;
//    }

    @PostMapping("/{id}/devolucion")
    public PrestamoResponse devolver(@PathVariable Long id) { return prestamoService.devolver(id); }
}

package com.usta.biblioteca.dto;

public record PrestamoResponse(Long id, LibroResponse libro, UsuarioResponse usuario,
        java.time.LocalDate fechaPrestamo, java.time.LocalDate fechaDevolucionPrevista,
        java.time.LocalDate fechaDevolucion, com.usta.biblioteca.domain.EstadoPrestamo estado) {}

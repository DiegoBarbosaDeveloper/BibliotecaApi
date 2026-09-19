package com.usta.biblioteca.dto;

public record LibroResponse(Long id, String titulo, String autor, String isbn, boolean disponible) {}

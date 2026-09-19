package com.usta.biblioteca.dto;

import jakarta.validation.constraints.NotNull;

public record PrestamoRequest(
        @NotNull(message = "El libro es obligatorio") Long libroId,
        @NotNull(message = "El usuario es obligatorio") Long usuarioId) {}

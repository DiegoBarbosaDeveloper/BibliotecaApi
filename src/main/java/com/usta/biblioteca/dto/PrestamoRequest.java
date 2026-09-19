package com.usta.biblioteca.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PrestamoRequest(
        @NotNull @Positive(message = "El libro es obligatorio") Long libroId,
        @NotNull @Positive (message = "El usuario es obligatorio") Long usuarioId) {}

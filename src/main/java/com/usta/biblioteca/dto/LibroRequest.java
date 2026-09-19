package com.usta.biblioteca.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LibroRequest(
    // TODO: Agregar los componentes del contrato Swagger y las validaciones correspondientes.
    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    String titulo,

    @NotBlank(message = "El autor no puede estar vacío")
    @Size(max = 100, message = "El autor no puede superar los 100 carcateres")
    String autor,

    @NotBlank(message = "El ISBN no puede estar vacío")
    @Size (min = 10, max = 15, message = "El ISBN debe tener entre 10 y 13 caracteres")
    String isbn


){}

package com.usta.biblioteca.mapper;

import org.mapstruct.Mapper;
import com.usta.biblioteca.domain.Libro;
import com.usta.biblioteca.dto.LibroResponse;

@Mapper(componentModel = "spring")
public interface LibroMapper {
    LibroResponse toResponse(Libro entidad);
}

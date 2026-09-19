package com.usta.biblioteca.mapper;

import com.usta.biblioteca.dto.LibroRequest;
import org.mapstruct.Mapper;
import com.usta.biblioteca.domain.Libro;
import com.usta.biblioteca.dto.LibroResponse;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LibroMapper {
    LibroResponse toResponse(Libro entidad);
    Libro toEntity(LibroRequest entidad);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", ignore = true)   // R10: no se edita desde el request
    void update(@MappingTarget Libro libro, LibroRequest request);

}

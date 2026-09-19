package com.usta.biblioteca.mapper;

import org.mapstruct.Mapper;
import com.usta.biblioteca.domain.Prestamo;
import com.usta.biblioteca.dto.PrestamoResponse;

@Mapper(componentModel = "spring", uses = {LibroMapper.class, UsuarioMapper.class})
public interface PrestamoMapper {
    PrestamoResponse toResponse(Prestamo entidad);
}

package com.usta.biblioteca.mapper;

import org.mapstruct.Mapper;
import com.usta.biblioteca.domain.Usuario;
import com.usta.biblioteca.dto.UsuarioResponse;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponse toResponse(Usuario entidad);
}

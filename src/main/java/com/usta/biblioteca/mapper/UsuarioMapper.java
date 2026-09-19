package com.usta.biblioteca.mapper;

import com.usta.biblioteca.dto.UsuarioRequest;
import org.mapstruct.Mapper;
import com.usta.biblioteca.domain.Usuario;
import com.usta.biblioteca.dto.UsuarioResponse;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponse toResponse(Usuario entidad);

    Usuario toEntity(UsuarioRequest request);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Usuario usuario, UsuarioRequest request);
}

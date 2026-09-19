package com.usta.biblioteca.service;

import com.usta.biblioteca.domain.EstadoPrestamo;
import com.usta.biblioteca.domain.Prestamo;
import com.usta.biblioteca.domain.Usuario;
import com.usta.biblioteca.dto.UsuarioRequest;
import com.usta.biblioteca.dto.UsuarioResponse;
import com.usta.biblioteca.mapper.UsuarioMapper;
import com.usta.biblioteca.repository.PrestamoRepository;
import com.usta.biblioteca.repository.UsuarioRepository;
import com.usta.biblioteca.view.exception.BusinessRuleException;
import com.usta.biblioteca.view.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PrestamoRepository prestamoRepository;

    public List<UsuarioResponse> getUsuarios(){
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    public UsuarioResponse getUsuarioById(@NonNull Long id){
        if(!usuarioRepository.existsById(id)){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        if(usuarioRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return usuarioMapper.toResponse(usuarioRepository.findById(id).get());
    }

    public UsuarioResponse save(@NonNull UsuarioRequest usuarioRequest){
        if(usuarioRepository.existsByEmail(usuarioRequest.email())){
           throw new BusinessRuleException("Usuario existente");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse update(@NonNull Long id, @NonNull UsuarioRequest usuarioRequest){
        if(!usuarioRepository.existsById(id)){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        if(usuarioRepository.existsByEmailAndIdNot(usuarioRequest.email(), id)){
            throw new BusinessRuleException("Email en uso");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);

        usuario.setId(id);
        usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuario);
    }

    public void deleteById(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

//        var lista = prestamoRepository.findAll()
//                .stream()
//                        .filter(x -> x.getUsuario().getId().equals(id));
//
        if (prestamoRepository.findAll().stream().anyMatch(x -> x.getUsuario().getId().equals(id))) {
            throw new BusinessRuleException("Usuario contiene prestamos");
        }


        usuarioRepository.deleteById(id);
    }

}

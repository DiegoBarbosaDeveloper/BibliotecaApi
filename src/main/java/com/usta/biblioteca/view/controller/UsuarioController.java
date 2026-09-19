package com.usta.biblioteca.view.controller;

import com.usta.biblioteca.domain.Libro;
import com.usta.biblioteca.dto.UsuarioRequest;
import com.usta.biblioteca.dto.UsuarioResponse;
import com.usta.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor


public class UsuarioController {
    // TODO: Agregar endpoints /api/v1, validación de entrada y delegación al servicio.
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>>getAllUsuarios(){
        List<UsuarioResponse>usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse>getUsuariosById(@PathVariable Long id){
        UsuarioResponse usuario = usuarioService.getUsuarioById(id);
        return  ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse>createUsuario(@Valid @RequestBody UsuarioRequest request){
        UsuarioResponse nuevoUsuario = usuarioService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse>updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request){
        UsuarioResponse usuarioActualizado = usuarioService.update(id, request);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteUsuario(@PathVariable Long id){
        usuarioService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

}

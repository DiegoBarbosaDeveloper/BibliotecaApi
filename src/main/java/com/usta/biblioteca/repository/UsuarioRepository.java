package com.usta.biblioteca.repository;

import com.usta.biblioteca.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}

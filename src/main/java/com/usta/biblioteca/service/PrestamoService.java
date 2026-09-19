package com.usta.biblioteca.service;

import com.usta.biblioteca.domain.*;
import com.usta.biblioteca.dto.*;
import com.usta.biblioteca.mapper.PrestamoMapper;
import com.usta.biblioteca.repository.*;
import com.usta.biblioteca.view.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;
    private final PrestamoMapper prestamoMapper;

    public List<PrestamoResponse> listar(EstadoPrestamo estado, Long libroId) {
        return prestamoRepository.buscar(estado, libroId).stream()
                .map(prestamoMapper::toResponse).toList();
    }

    public PrestamoResponse obtener(Long id) {
        return prestamoMapper.toResponse(prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con id " + id)));
    }

    @Transactional
    public PrestamoResponse prestar(PrestamoRequest request) {
        Libro libro = libroRepository.findByIdForUpdate(request.libroId())
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id " + request.libroId()));
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + request.usuarioId()));
        if (!libro.isDisponible()) {
            throw new BusinessRuleException("El libro «" + libro.getTitulo() + "» ya está prestado");
        }
        LocalDate hoy = LocalDate.now();
        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(hoy);
        prestamo.setFechaDevolucionPrevista(hoy.plusDays(14));
        prestamo.setEstado(EstadoPrestamo.PRESTADO);
        libro.setDisponible(false);
        prestamoRepository.save(prestamo);
        log.info("Préstamo {} registrado para el libro {}", prestamo.getId(), libro.getId());
        return prestamoMapper.toResponse(prestamo);
    }

    @Transactional
    public PrestamoResponse devolver(Long id) {
        Prestamo prestamo = prestamoRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con id " + id));
        if (prestamo.getEstado() != EstadoPrestamo.PRESTADO) {
            throw new BusinessRuleException("El préstamo " + id + " ya fue devuelto");
        }
        Libro libro = libroRepository.findByIdForUpdate(prestamo.getLibro().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Libro del préstamo no encontrado"));
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        libro.setDisponible(true);
        log.info("Devolución registrada para el préstamo {}", id);
        return prestamoMapper.toResponse(prestamo);
    }
}

package com.usta.biblioteca.repository;

import com.usta.biblioteca.domain.*;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    @Query("""
        select p from Prestamo p
        where (:estado is null or p.estado = :estado)
          and (:libroId is null or p.libro.id = :libroId)
        order by p.fechaPrestamo desc, p.id desc
        """)
    List<Prestamo> buscar(@Param("estado") EstadoPrestamo estado, @Param("libroId") Long libroId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Prestamo p where p.id = :id")
    Optional<Prestamo> findByIdForUpdate(@Param("id") Long id);
}

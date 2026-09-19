package com.usta.biblioteca.repository;

import com.usta.biblioteca.domain.Libro;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Serializa los préstamos concurrentes del mismo libro.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Libro l where l.id = :id")
    Optional<Libro> findByIdForUpdate(@Param("id") Long id);
}

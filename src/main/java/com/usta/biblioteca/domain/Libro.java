package com.usta.biblioteca.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 200)
    private String titulo;
    @Column(nullable = false, length = 150)
    private String autor;
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;
    @Column(nullable = false)
    private boolean disponible = true;
}

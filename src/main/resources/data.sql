-- Datos iniciales: 4 libros disponibles y 3 usuarios.
-- Los préstamos se crean mediante la API durante el taller.

INSERT INTO libros (titulo, autor, isbn, disponible) VALUES
  ('Cien años de soledad', 'Gabriel García Márquez', '978-0307474728', TRUE),
  ('El principito', 'Antoine de Saint-Exupéry', '978-0156012195', TRUE),
  ('Don Quijote de la Mancha', 'Miguel de Cervantes', '978-8420412146', TRUE),
  ('Crimen y castigo', 'Fiódor Dostoyevski', '978-8420674881', TRUE);

INSERT INTO usuarios (nombre, email) VALUES
  ('Ana Martínez', 'ana.martinez@example.com'),
  ('Carlos Pérez', 'carlos.perez@example.com'),
  ('Laura Gómez', 'laura.gomez@example.com');

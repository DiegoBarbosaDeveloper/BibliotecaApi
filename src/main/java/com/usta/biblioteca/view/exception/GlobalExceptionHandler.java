package com.usta.biblioteca.view.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.ErrorResponse;
import java.net.URI;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail notFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return problem(404, "No encontrado", ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ProblemDetail conflict(BusinessRuleException ex, HttpServletRequest request) {
        return problem(409, "Conflicto de regla de negocio", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail p = problem(400, "Solicitud inválida", "La validación de la solicitud falló", request);
        p.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
                .map(e -> Map.of("field", e.getField(), "message",
                        e.getDefaultMessage() == null ? "Valor inválido" : e.getDefaultMessage())).toList());
        return p;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail unreadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return problem(400, "Solicitud inválida", "El cuerpo JSON está ausente, mal formado o contiene tipos incompatibles", request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail parameter(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return problem(400, "Parámetro inválido", "El valor del parámetro «" + ex.getName() + "» no es válido", request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail unexpected(Exception ex, HttpServletRequest request) {
        // Conservar los códigos HTTP de errores MVC (405, 415, etc.).
        if (ex instanceof ErrorResponse error) {
            ProblemDetail p = error.getBody();
            p.setInstance(URI.create(request.getRequestURI()));
            return p;
        }
        log.error("Error no controlado en {}", request.getRequestURI(), ex);
        return problem(500, "Error interno", "Ocurrió un error inesperado", request);
    }

    // El contrato exige type incluso cuando su valor es about:blank.
    public static class ApiProblem extends ProblemDetail {
        public ApiProblem(int status) { super(status); }

        @Override
        @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS)
        public URI getType() { return super.getType(); }
    }

    private ProblemDetail problem(int status, String title, String detail, HttpServletRequest request) {
        ProblemDetail p = new ApiProblem(status);
        p.setDetail(detail);
        p.setTitle(title);
        p.setInstance(URI.create(request.getRequestURI()));
        return p;
    }
}

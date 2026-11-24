package org.example.catalogodecamisetasv2.exception;

import org.example.catalogodecamisetasv2.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Centraliza el manejo de excepciones de negocio para evitar duplicación
 * de bloques try/catch en los controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de recurso no encontrado para Camiseta.
     * @param ex Excepción lanzada
     * @return Respuesta 404 Not Found con mensaje de error
     */
    @ExceptionHandler(CamisetaNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> manejarCamisetaNotFoundException(CamisetaNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja excepciones de recurso no encontrado para Equipo.
     * @param ex Excepción lanzada
     * @return Respuesta 404 Not Found con mensaje de error
     */
    @ExceptionHandler(EquipoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> manejarEquipoNotFoundException(EquipoNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja excepciones de recurso no encontrado para Competición.
     * @param ex Excepción lanzada
     * @return Respuesta 404 Not Found con mensaje de error
     */
    @ExceptionHandler(CompeticionNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> manejarCompeticionNotFoundException(CompeticionNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja excepciones de recurso no encontrado para País.
     * @param ex Excepción lanzada
     * @return Respuesta 404 Not Found con mensaje de error
     */
    @ExceptionHandler(PaisNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> manejarPaisNotFoundException(PaisNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja excepciones de datos inválidos.
     * @param ex Excepción lanzada
     * @return Respuesta 400 Bad Request con mensaje de error
     */
    @ExceptionHandler(DatosInvalidosException.class)
    public ResponseEntity<ErrorResponseDTO> manejarDatosInvalidosException(DatosInvalidosException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(400, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Maneja excepciones de mapeo de datos.
     * @param ex Excepción lanzada
     * @return Respuesta 500 Internal Server Error con mensaje de error
     */
    @ExceptionHandler(MappingException.class)
    public ResponseEntity<ErrorResponseDTO> manejarMappingException(MappingException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(500, "Error interno al procesar datos: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Maneja excepciones genéricas del repositorio.
     * @param ex Excepción lanzada
     * @return Respuesta 500 Internal Server Error con mensaje de error
     */
    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<ErrorResponseDTO> manejarRepositoryException(RepositoryException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(500, "Error al acceder a la base de datos: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    /**
     * Meneja excepciones de los metodos @valid
     * @param ex Excepción lanzada
     * @return Respuesta 400 Bad Request con mensaje de error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> manejarMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce("", (acc, error) -> acc + error + "; ");
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(400, "Datos inválidos: " + mensaje);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> manejarHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(400, "Solicitud inválida: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> manejarHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(415, "Tipo de medio no soportado: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorResponse);
    }
    /**
     * Maneja excepciones genéricas no capturadas.
     * @param ex Excepción lanzada
     * @return Respuesta 500 Internal Server Error con mensaje de error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> manejarExcepcionGeneral(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(500, "Error interno del servidor: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}


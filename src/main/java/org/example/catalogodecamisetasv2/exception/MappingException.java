package org.example.catalogodecamisetasv2.exception;

/**
 * Excepción lanzada cuando ocurre un error durante el mapeo de entidades a DTOs.
 * Se utiliza en la capa de mapper para indicar problemas de transformación de datos.
 * Esta excepción es una RuntimeException que generalmente indica un error interno del servidor.
 */
public class MappingException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje de error descriptivo
     */
    public MappingException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje personalizado y causa.
     *
     * @param mensaje Mensaje de error descriptivo
     * @param causa Excepción que causó este error
     */
    public MappingException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor con causa.
     *
     * @param causa Excepción que causó este error
     */
    public MappingException(Throwable causa) {
        super(causa);
    }
}


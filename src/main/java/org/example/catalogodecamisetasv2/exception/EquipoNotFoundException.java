package org.example.catalogodecamisetasv2.exception;

/**
 * Excepción lanzada cuando un equipo no es encontrado en la base de datos.
 * Esta excepción es una RuntimeException que puede ser capturada en el controlador
 * para devolver una respuesta HTTP 404 Not Found.
 */
public class EquipoNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje de error descriptivo
     */
    public EquipoNotFoundException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje personalizado y causa.
     *
     * @param mensaje Mensaje de error descriptivo
     * @param causa Excepción que causó este error
     */
    public EquipoNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor con causa.
     *
     * @param causa Excepción que causó este error
     */
    public EquipoNotFoundException(Throwable causa) {
        super(causa);
    }
}


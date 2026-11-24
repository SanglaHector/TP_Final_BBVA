package org.example.catalogodecamisetasv2.exception;

/**
 * Excepción lanzada cuando un usuario no es encontrado en la base de datos.
 * Esta excepción es una RuntimeException que puede ser capturada en el controlador
 * para devolver una respuesta HTTP 404 Not Found.
 */
public class UsuarioNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje de error descriptivo
     */
    public UsuarioNotFoundException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje personalizado y causa.
     *
     * @param mensaje Mensaje de error descriptivo
     * @param causa Excepción que causó este error
     */
    public UsuarioNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor con causa.
     *
     * @param causa Excepción que causó este error
     */
    public UsuarioNotFoundException(Throwable causa) {
        super(causa);
    }
}


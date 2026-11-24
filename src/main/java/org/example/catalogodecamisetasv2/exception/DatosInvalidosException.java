package org.example.catalogodecamisetasv2.exception;

/**
 * Excepción lanzada cuando los datos proporcionados no cumplen con las validaciones de negocio.
 * Se utiliza en la capa de servicio para validaciones más allá de las restricciones básicas.
 * Esta excepción es una RuntimeException que puede ser capturada en el controlador
 * para devolver una respuesta HTTP 400 Bad Request.
 */
public class DatosInvalidosException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje de error descriptivo
     */
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje personalizado y causa.
     *
     * @param mensaje Mensaje de error descriptivo
     * @param causa Excepción que causó este error
     */
    public DatosInvalidosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor con causa.
     *
     * @param causa Excepción que causó este error
     */
    public DatosInvalidosException(Throwable causa) {
        super(causa);
    }
}


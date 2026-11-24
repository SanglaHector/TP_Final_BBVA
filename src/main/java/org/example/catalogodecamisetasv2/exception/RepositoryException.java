package org.example.catalogodecamisetasv2.exception;

/**
 * Excepción lanzada cuando ocurre un error en la capa de persistencia (repositorio).
 * Puede ser causada por problemas de lectura/escritura de datos, errores de JSON, etc.
 * Se utiliza en la capa de repositorio para envolver excepciones de acceso a datos.
 * Esta excepción es una RuntimeException que indica un error interno del servidor (500).
 */
public class RepositoryException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param mensaje Mensaje de error descriptivo
     */
    public RepositoryException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor con mensaje personalizado y causa.
     *
     * @param mensaje Mensaje de error descriptivo
     * @param causa Excepción que causó este error
     */
    public RepositoryException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /**
     * Constructor con causa.
     *
     * @param causa Excepción que causó este error
     */
    public RepositoryException(Throwable causa) {
        super(causa);
    }
}


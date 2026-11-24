package org.example.catalogodecamisetasv2.model;

/**
 * Enum que define los tipos válidos de equipo.
 */
public enum TipoEquipo {
    CLUB("Club"),
    SELECCION("Selección Nacional");

    private final String descripcion;

    TipoEquipo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}


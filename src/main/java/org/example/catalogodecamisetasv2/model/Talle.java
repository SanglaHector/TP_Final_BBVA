package org.example.catalogodecamisetasv2.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum que define los talles válidos para una camiseta.
 */
public enum Talle {
    XS("Extra Small"),
    S("Small"),
    M("Medium"),
    L("Large"),
    XL("Extra Large"),
    XXL("2 Extra Large");

    private final String descripcion;

    Talle(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Permite la serialización del enum como su nombre (XS, S, M, etc.)
     * Esto es importante para la compatibilidad con MongoDB
     */
    @JsonValue
    public String toValue() {
        return this.name();
    }

    /**
     * Permite la deserialización del enum desde un string (XS, S, M, etc.)
     * Esto es importante para mapear datos de MongoDB correctamente
     */
    @JsonCreator(mode = com.fasterxml.jackson.annotation.JsonCreator.Mode.DELEGATING)
    public static Talle fromValue(String value) {
        if (value == null) {
            return null;
        }
        // Intentar primero con el nombre exacto
        try {
            return Talle.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e1) {
            // Si no funciona, intentar con case-insensitive
            for (Talle talle : Talle.values()) {
                if (talle.name().equalsIgnoreCase(value)) {
                    return talle;
                }
            }
            // Si aún no funciona, retornar null en lugar de lanzar excepción
            // esto permite que MongoDB continúe cargando otros documentos
            return null;
        }
    }
}


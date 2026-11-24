package org.example.catalogodecamisetasv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.catalogodecamisetasv2.model.Talle;

/**
 * DTO de respuesta para Camiseta.
 * Se usa para enviar datos al front sin exponer la entidad Camiseta.
 * Estructura simplificada y más legible que la entity completa.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CamisetaResponseDTO {
    private String id;
    private String marca;
    private Talle talle;
    private Double precio;
    private Integer stock;
    private String temporada;
    private Boolean esRetro;

    // Información resumida del equipo
    private EquipoResumido equipo;

    /**
     * DTO anidado para representar el Equipo de forma simplificada
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EquipoResumido {
        private String nombre;
        private String tipo;  // CLUB, SELECCION
        private PaisResumido pais;
        private CompeticionResumida competicion;
    }

    /**
     * DTO anidado para representar el País de forma simplificada
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaisResumido {
        private String nombre;
    }

    /**
     * DTO anidado para representar la Competición de forma simplificada
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CompeticionResumida {
        private String nombre;
    }
}

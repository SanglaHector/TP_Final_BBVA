package org.example.catalogodecamisetasv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta para Competición.
 * Se usa para enviar datos al front sin exponer la entidad Competición.
 * Estructura simplificada y más legible que la entity completa.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompeticionResponseDTO {
    private String id;
    private String nombre;

    // Información resumida del país
    private PaisResumido pais;

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
}


package org.example.catalogodecamisetasv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta para País.
 * Se usa para enviar datos al front sin exponer la entidad País.
 * Estructura simplificada y más legible que la entity completa.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaisResponseDTO {
    private String id;
    private String nombre;
}


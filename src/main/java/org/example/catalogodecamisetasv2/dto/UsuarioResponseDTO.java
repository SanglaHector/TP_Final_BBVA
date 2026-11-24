package org.example.catalogodecamisetasv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.catalogodecamisetasv2.model.Rol;

/**
 * DTO de respuesta para Usuario.
 * Se usa para enviar datos al front sin exponer la entidad Usuario.
 * Estructura simplificada y más legible que la entity completa.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private String id;
    private String nombre;
    private Rol rol;
}


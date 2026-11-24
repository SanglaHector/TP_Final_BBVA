package org.example.catalogodecamisetasv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.catalogodecamisetasv2.model.TipoEquipo;

/**
 * DTO de solicitud para Equipo.
 * Se usa para validar los datos que envía el frontend al crear/actualizar un equipo.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoRequestDTO {

    @NotBlank(message = "El nombre del equipo es obligatorio")
    private String nombre;

    @NotNull(message = "El ID del país es obligatorio")
    private String paisId;

    @NotNull(message = "El ID de la competición es obligatorio")
    private String competicionId;

    @NotNull(message = "El tipo de equipo es obligatorio")
    private TipoEquipo tipo;
}




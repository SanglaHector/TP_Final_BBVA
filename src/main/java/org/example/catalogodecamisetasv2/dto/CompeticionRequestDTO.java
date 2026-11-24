package org.example.catalogodecamisetasv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de solicitud para Competición.
 * Se usa para validar los datos que envía el frontend al crear/actualizar una competición.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompeticionRequestDTO {

    @NotBlank(message = "El nombre de la competición es obligatorio")
    private String nombre;

    @NotNull(message = "El ID del país es obligatorio")
    private String paisId;
}


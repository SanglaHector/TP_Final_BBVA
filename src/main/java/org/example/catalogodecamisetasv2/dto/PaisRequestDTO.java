package org.example.catalogodecamisetasv2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de solicitud para País.
 * Se usa para validar los datos que envía el frontend al crear/actualizar un país.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaisRequestDTO {

    @NotBlank(message = "El nombre del país es obligatorio")
    private String nombre;
}


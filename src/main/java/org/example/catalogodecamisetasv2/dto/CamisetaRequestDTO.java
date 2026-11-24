package org.example.catalogodecamisetasv2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.catalogodecamisetasv2.model.Talle;
import org.example.catalogodecamisetasv2.validation.ValidTemporada;

/**
 * DTO de solicitud para Camiseta.
 * Se usa para validar los datos que envía el frontend al crear/actualizar una camiseta.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CamisetaRequestDTO {

    @NotNull(message = "El ID del equipo es obligatorio")
    private String equipoId;

    @NotBlank(message = "La temporada es obligatoria")
    @ValidTemporada
    private String temporada;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Double precio;

    @NotNull(message = "El talle es obligatorio")
    private Talle talle;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @Builder.Default
    private Boolean esRetro = false;
}



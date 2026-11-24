package org.example.catalogodecamisetasv2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.catalogodecamisetasv2.model.Talle;
import org.example.catalogodecamisetasv2.model.TipoEquipo;
import org.example.catalogodecamisetasv2.validation.ValidTemporada;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AltaCompletaRequestDTO {
    @NotNull(message = "La temporada es obligatoria")
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

    @NotNull(message = "La marca debe ser obligatoria")
    private String marca;

    @NotNull(message = "El indicador de retro debe ser obligatorio")
    private Boolean esRetro;

    @NotNull(message = "El nombre del equipo es obligatorio")
    private String nombreEquipo;

    @NotNull(message = "El tipo de equipo es obligatorio")
    private TipoEquipo tipo;

    @NotNull(message = "El nombre de la competición es obligatorio")
    private String nombreCompeticion;

    @NotNull(message = "El nombre del país es obligatorio")
    private String nombrePais;
}

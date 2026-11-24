package org.example.catalogodecamisetasv2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de solicitud para Usuario.
 * Se usa para validar los datos que envía el frontend para login u operaciones con usuarios.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    private String clave;
}


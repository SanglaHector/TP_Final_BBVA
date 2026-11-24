package org.example.catalogodecamisetasv2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.catalogodecamisetasv2.model.Rol;

/**
 * DTO de creación para Usuario.
 * Se usa para validar los datos que envía el frontend al crear un usuario.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreateDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatoria")
    private String clave;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;
}


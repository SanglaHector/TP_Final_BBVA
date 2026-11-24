package org.example.catalogodecamisetasv2.mapper;

import org.example.catalogodecamisetasv2.dto.UsuarioResponseDTO;
import org.example.catalogodecamisetasv2.model.Usuario;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Usuario (entity), UsuarioRequestDTO y UsuarioResponseDTO.
 * Simplifica la transformación de datos para las respuestas API y validaciones de entrada.
 */
@Component
public class UsuarioMapper {

    /**
     * Convierte un Usuario en UsuarioResponseDTO.
     *
     * @param usuario Entidad Usuario
     * @return DTO de respuesta con estructura simplificada
     */
    public UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .rol(usuario.getRol())
                .build();
    }
}


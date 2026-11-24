package org.example.catalogodecamisetasv2.mapper;

import org.example.catalogodecamisetasv2.dto.PaisResponseDTO;
import org.example.catalogodecamisetasv2.model.Pais;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Pais (entity), PaisRequestDTO y PaisResponseDTO.
 * Simplifica la transformación de datos para las respuestas API y validaciones de entrada.
 */
@Component
public class PaisMapper {

    /**
     * Convierte un País en PaisResponseDTO.
     *
     * @param pais Entidad País
     * @return DTO de respuesta con estructura simplificada
     */
    public PaisResponseDTO toPaisResponseDTO(Pais pais) {
        if (pais == null) {
            return null;
        }

        return PaisResponseDTO.builder()
                .id(pais.getId())
                .nombre(pais.getNombre())
                .build();
    }
}


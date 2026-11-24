package org.example.catalogodecamisetasv2.mapper;

import org.example.catalogodecamisetasv2.dto.CompeticionResponseDTO;
import org.example.catalogodecamisetasv2.model.Competicion;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Competicion (entity), CompeticionRequestDTO y CompeticionResponseDTO.
 * Simplifica la transformación de datos para las respuestas API y validaciones de entrada.
 */
@Component
public class CompeticionMapper {

    /**
     * Convierte una Competicion en CompeticionResponseDTO.
     *
     * @param competicion Entidad Competicion
     * @return DTO de respuesta con estructura simplificada
     */
    public CompeticionResponseDTO toCompeticionResponseDTO(Competicion competicion) {
        if (competicion == null) {
            return null;
        }

        return CompeticionResponseDTO.builder()
                .id(competicion.getId())
                .nombre(competicion.getNombre())
                .pais(mapPais(competicion.getPais()))
                .build();
    }

    /**
     * Mapea un País a PaisResumido (sin ID).
     *
     * @param pais País a mapear
     * @return PaisResumido simplificado
     */
    private CompeticionResponseDTO.PaisResumido mapPais(org.example.catalogodecamisetasv2.model.Pais pais) {
        if (pais == null) {
            return null;
        }

        return CompeticionResponseDTO.PaisResumido.builder()
                .nombre(pais.getNombre())
                .build();
    }
}


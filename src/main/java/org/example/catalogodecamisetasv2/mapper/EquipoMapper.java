package org.example.catalogodecamisetasv2.mapper;

import org.example.catalogodecamisetasv2.dto.EquipoResponseDTO;
import org.example.catalogodecamisetasv2.model.Competicion;
import org.example.catalogodecamisetasv2.model.Equipo;
import org.example.catalogodecamisetasv2.model.Pais;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Equipo (entity), EquipoRequestDTO y EquipoResponseDTO.
 * Simplifica la transformación de datos para las respuestas API y validaciones de entrada.
 */
@Component
public class EquipoMapper {

    /**
     * Convierte un Equipo en EquipoResponseDTO.
     *
     * @param equipo Entidad Equipo
     * @return DTO de respuesta con estructura simplificada
     */
    public EquipoResponseDTO toEquipoResponseDTO(Equipo equipo) {
        if (equipo == null) {
            return null;
        }

        return EquipoResponseDTO.builder()
                .id(equipo.getId())
                .nombre(equipo.getNombre())
                .tipo(equipo.getTipo())
                .pais(mapPais(equipo.getPais()))
                .competicion(mapCompeticion(equipo.getCompeticion()))
                .build();
    }

    /**
     * Mapea un País a PaisResumido (sin ID).
     *
     * @param pais País a mapear
     * @return PaisResumido simplificado
     */
    private EquipoResponseDTO.PaisResumido mapPais(Pais pais) {
        if (pais == null) {
            return null;
        }

        return EquipoResponseDTO.PaisResumido.builder()
                .nombre(pais.getNombre())
                .build();
    }

    /**
     * Mapea una Competición a CompeticionResumida (sin ID).
     *
     * @param competicion Competición a mapear
     * @return CompeticionResumida simplificada
     */
    private EquipoResponseDTO.CompeticionResumida mapCompeticion(Competicion competicion) {
        if (competicion == null) {
            return null;
        }

        return EquipoResponseDTO.CompeticionResumida.builder()
                .nombre(competicion.getNombre())
                .build();
    }
}


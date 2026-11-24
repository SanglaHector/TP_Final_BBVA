package org.example.catalogodecamisetasv2.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.catalogodecamisetasv2.dto.CamisetaResponseDTO;
import org.example.catalogodecamisetasv2.exception.MappingException;
import org.example.catalogodecamisetasv2.model.Camiseta;
import org.example.catalogodecamisetasv2.model.Equipo;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.EquipoRepository;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Camiseta (entity), CamisetaRequestDTO y CamisetaResponseDTO.
 * Simplifica la transformación de datos para las respuestas API y validaciones de entrada.
 */
@Component
@Slf4j
public class CamisetaMapper {

    private final EquipoRepository equipoRepository;

    public CamisetaMapper(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    /**
     * Convierte una Camiseta en CamisetaResponseDTO.
     *
     * @param camiseta Entidad Camiseta
     * @return DTO de respuesta con estructura simplificada
     * @throws MappingException si la camiseta tiene datos inconsistentes
     */
    public CamisetaResponseDTO toCamisetaResponseDTO(Camiseta camiseta) {
        if (camiseta == null) {
            return null;
        }

        // Validar que los campos críticos no sean nulos
        if (camiseta.getId() == null) {
            throw new MappingException("No se puede mapear una Camiseta sin ID");
        }
        if (camiseta.getMarca() == null || camiseta.getMarca().isBlank()) {
            throw new MappingException("No se puede mapear una Camiseta sin marca");
        }
        if (camiseta.getTalle() == null) {
            throw new MappingException("No se puede mapear una Camiseta sin talle");
        }
        if (camiseta.getPrecio() == null) {
            throw new MappingException("No se puede mapear una Camiseta sin precio");
        }
        if (camiseta.getPrecio() < 0){
            throw new MappingException("No se puede mapear una Camiseta con precio negativo");
        }
        if (camiseta.getStock() == null) {
            throw new MappingException("No se puede mapear una Camiseta sin stock");
        }
        if (camiseta.getStock() < 0){
            throw new MappingException("No se puede mapear una Camiseta con stock negativo");
        }
        if (camiseta.getTemporada() == null || camiseta.getTemporada().isBlank()) {
            throw new MappingException("No se puede mapear una Camiseta sin temporada");
        }
        return CamisetaResponseDTO.builder()
                .id(camiseta.getId())
                .marca(camiseta.getMarca())
                .talle(camiseta.getTalle())
                .precio(camiseta.getPrecio())
                .stock(camiseta.getStock())
                .temporada(camiseta.getTemporada())
                .esRetro(camiseta.getEsRetro())
                .equipo(mapEquipoDirecto(camiseta.getEquipo()))
                .build();
    }

    /**
     * Mapea un Equipo directamente a EquipoResumido.
     *
     * @param equipo Objeto Equipo a mapear
     * @return EquipoResumido simplificado
     * @throws MappingException si el equipo tiene datos inconsistentes
     */
    private CamisetaResponseDTO.EquipoResumido mapEquipoDirecto(Equipo equipo) {
        if (equipo == null) {
            throw new MappingException("No se puede mapear una Camiseta sin Equipo asociado");
        }

        if (equipo.getNombre() == null || equipo.getNombre().isBlank()) {
            throw new MappingException("No se puede mapear un Equipo sin nombre");
        }
        if (equipo.getTipo() == null) {
            throw new MappingException("No se puede mapear un Equipo sin tipo");
        }

        return CamisetaResponseDTO.EquipoResumido.builder()
                .nombre(equipo.getNombre())
                .tipo(equipo.getTipo().name())
                .pais(mapPais(equipo.getPais()))
                .competicion(mapCompeticion(equipo.getCompeticion()))
                .build();
    }

    /**
     * Mapea un Equipo a EquipoResumido (sin IDs anidados).
     *
     * @param equipoId ID del Equipo a mapear
     * @return EquipoResumido simplificado
     * @throws MappingException si el equipo tiene datos inconsistentes
     */
    private CamisetaResponseDTO.EquipoResumido mapEquipo(String equipoId) {
        if (equipoId == null || equipoId.isBlank()) {
            throw new MappingException("No se puede mapear una Camiseta sin Equipo asociado");
        }

        var equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new MappingException("No se encontró Equipo con ID: " + equipoId));

        if (equipo.getNombre() == null || equipo.getNombre().isBlank()) {
            throw new MappingException("No se puede mapear un Equipo sin nombre");
        }
        if (equipo.getTipo() == null) {
            throw new MappingException("No se puede mapear un Equipo sin tipo");
        }

        return CamisetaResponseDTO.EquipoResumido.builder()
                .nombre(equipo.getNombre())
                .tipo(equipo.getTipo().name())
                .pais(mapPais(equipo.getPais()))
                .competicion(mapCompeticion(equipo.getCompeticion()))
                .build();
    }

    /**
     * Mapea un País a PaisResumido (sin ID).
     *
     * @param pais País a mapear
     * @return PaisResumido simplificado
     * @throws MappingException si el país tiene datos inconsistentes
     */
    private CamisetaResponseDTO.PaisResumido mapPais(org.example.catalogodecamisetasv2.model.Pais pais) {
        if (pais == null) {
            throw new MappingException("No se puede mapear un Equipo sin País asociado");
        }

        if (pais.getNombre() == null || pais.getNombre().isBlank()) {
            throw new MappingException("No se puede mapear un País sin nombre");
        }

        return CamisetaResponseDTO.PaisResumido.builder()
                .nombre(pais.getNombre())
                .build();
    }

    /**
     * Mapea una Competición a CompeticionResumida (sin ID).
     *
     * @param competicion Competición a mapear
     * @return CompeticionResumida simplificada
     * @throws MappingException si la competición tiene datos inconsistentes
     */
    private CamisetaResponseDTO.CompeticionResumida mapCompeticion(org.example.catalogodecamisetasv2.model.Competicion competicion) {
        if (competicion == null) {
            throw new MappingException("No se puede mapear un Equipo sin Competición asociada");
        }

        if (competicion.getNombre() == null || competicion.getNombre().isBlank()) {
            throw new MappingException("No se puede mapear una Competición sin nombre");
        }

        return CamisetaResponseDTO.CompeticionResumida.builder()
                .nombre(competicion.getNombre())
                .build();
    }
}

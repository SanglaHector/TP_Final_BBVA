package org.example.catalogodecamisetasv2.mapper;

import org.example.catalogodecamisetasv2.dto.*;
import org.example.catalogodecamisetasv2.model.TipoEquipo;
import org.springframework.stereotype.Component;

@Component
public class WorkFlowMapper {

    public CamisetaRequestDTO toCamisetaRequestDTO(AltaCompletaRequestDTO altaCompletaRequestDTO, String idPais, String idCompeticion, String idEquipo) {
        if (altaCompletaRequestDTO == null) {
            return null;
        }
        CamisetaRequestDTO camisetaRequestDTO = new CamisetaRequestDTO();
        camisetaRequestDTO.setMarca(altaCompletaRequestDTO.getMarca());
        camisetaRequestDTO.setTalle(altaCompletaRequestDTO.getTalle());
        camisetaRequestDTO.setPrecio(altaCompletaRequestDTO.getPrecio());
        camisetaRequestDTO.setStock(altaCompletaRequestDTO.getStock());
        camisetaRequestDTO.setTemporada(altaCompletaRequestDTO.getTemporada());
        camisetaRequestDTO.setEquipoId(idEquipo);

        return camisetaRequestDTO;
    }

    public CompeticionRequestDTO toCompeticionRequestDTO(String nombreCompeticion, String idPais) {
        if (nombreCompeticion == null) {
            return null;
        }
        CompeticionRequestDTO competicionRequestDTO = new CompeticionRequestDTO();
        competicionRequestDTO.setNombre(nombreCompeticion);
        competicionRequestDTO.setPaisId(idPais);
        return competicionRequestDTO;
    }

    public EquipoRequestDTO toEquipoRequestDTO(String nombreEquipo, TipoEquipo tipo,  String idPais, String competicionId) {
        if (nombreEquipo == null || idPais == null) {
            return null;
        }
        EquipoRequestDTO equipoRequestDTO = new EquipoRequestDTO();
        equipoRequestDTO.setNombre(nombreEquipo);
        equipoRequestDTO.setTipo(tipo);
        equipoRequestDTO.setPaisId(idPais);
        equipoRequestDTO.setCompeticionId(competicionId);
        return equipoRequestDTO;
    }

    public PaisRequestDTO toPaisRequestDTO(String nombrePais) {
        if (nombrePais == null) {
            return null;
        }
        PaisRequestDTO paisRequestDTO = new PaisRequestDTO();
        paisRequestDTO.setNombre(nombrePais);
        return paisRequestDTO;
    }

}

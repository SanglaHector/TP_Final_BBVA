package org.example.catalogodecamisetasv2.service;

import org.example.catalogodecamisetasv2.dto.*;
import org.example.catalogodecamisetasv2.mapper.*;
import org.example.catalogodecamisetasv2.model.Competicion;
import org.example.catalogodecamisetasv2.model.Equipo;
import org.example.catalogodecamisetasv2.model.Pais;
import org.example.catalogodecamisetasv2.model.TipoEquipo;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.CompeticionRepository;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.EquipoRepository;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.PaisRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class CamisetaWorkFlowService {
    private final EquipoRepository equipoRepository;
    private final CompeticionRepository competicionRepository;
    private final PaisRepository paisRepository;

    private final CamisetaService camisetaService;
    private final CompeticionService competicionService;
    private final EquipoService equipoService;
    private final PaisService paisService;

    private final WorkFlowMapper workFlowMapper;
    private final ExecutorService workflowExecutor;

    public CamisetaWorkFlowService (EquipoRepository equipoRepository,
                                    CompeticionRepository competicionRepository,
                                    PaisRepository paisRepository,
                                    CamisetaService camisetaService,
                                    CompeticionService competicionService,
                                    EquipoService equipoService,
                                    PaisService paisService,
                                    WorkFlowMapper workFlowMapper,
                                    @Qualifier("workflowExecutor") ExecutorService workflowExecutor) {

        this.equipoRepository = equipoRepository;
        this.competicionRepository = competicionRepository;
        this.paisRepository = paisRepository;
        this.camisetaService = camisetaService;
        this.competicionService = competicionService;
        this.equipoService = equipoService;
        this.paisService = paisService;
        this.workFlowMapper = workFlowMapper;
        this.workflowExecutor = workflowExecutor;
    }

    /**
     * Método transaccional que crea un alta completa (País, Competición, Equipo y Camiseta).
     * CONCURRENTE: Procesa País y Competición en paralelo (son independientes).
     * Luego Equipo y finalmente Camiseta.
     *
     * @param altaCompletaRequestDTO DTO con los datos de la alta completa
     * @return CamisetaResponseDTO de la camiseta creada
     * @throws Exception si ocurre un error durante la creación
     */
    @Transactional(
            rollbackFor = Exception.class,
            timeout = 60
    )
    public CamisetaResponseDTO guardarAltaCompletaAsync(AltaCompletaRequestDTO altaCompletaRequestDTO) {
        try {
            // PASO 1: Procesar País
            CompletableFuture<String> paisFuture = CompletableFuture.supplyAsync(
                    () -> findOrCreatePais(altaCompletaRequestDTO.getNombrePais()),
                    workflowExecutor
            );
            // PASO 2: Cuando temine pais, procesar Competencia
            CompletableFuture<String> competicionFuture = CompletableFuture.allOf(paisFuture)
                    .thenApplyAsync(
                            v -> findOrCreateCompeticion(altaCompletaRequestDTO.getNombreCompeticion(), paisFuture.join()),
                            workflowExecutor
                    );


            // PASO 3: Cuando termine Competencia, procesar Equipo
            CompletableFuture<String> equipoFuture = CompletableFuture.allOf(paisFuture, competicionFuture)
                    .thenApplyAsync(
                            v -> findOrCreateEquipo(
                                    altaCompletaRequestDTO.getNombreEquipo(),
                                    altaCompletaRequestDTO.getTipo(),
                                    paisFuture.join(),
                                    competicionFuture.join()
                            ),
                            workflowExecutor
                    );

            // PASO 4: Cuando todos estén listos, crear Camiseta
            CompletableFuture<CamisetaResponseDTO> camisetaFuture = CompletableFuture.allOf(
                    paisFuture, competicionFuture, equipoFuture
            ).thenApplyAsync(
                    v -> {
                        String paisId = paisFuture.join();
                        String competicionId = competicionFuture.join();
                        String equipoId = equipoFuture.join();

                        CamisetaRequestDTO camiseta = workFlowMapper.toCamisetaRequestDTO(
                                altaCompletaRequestDTO,
                                paisId,
                                competicionId,
                                equipoId
                        );
                        return camisetaService.guardar(camiseta);
                    },
                    workflowExecutor
            );

            // Obtener resultado final (bloquea hasta que termine)
            return camisetaFuture.join();

        } catch (Exception e) {
            throw new RuntimeException("Error en procesamiento concurrente: " + e.getMessage(), e);
        }
    }

    @Transactional(
            rollbackFor = Exception.class,
            timeout = 60
    )
    public CamisetaResponseDTO guardarAltaCompleta(AltaCompletaRequestDTO altaCompletaRequestDTO) {
        String paisId = findOrCreatePais(altaCompletaRequestDTO.getNombrePais());
        String competicionId = findOrCreateCompeticion(altaCompletaRequestDTO.getNombreCompeticion(), paisId);
        String equipoId = findOrCreateEquipo(altaCompletaRequestDTO.getNombreEquipo(), altaCompletaRequestDTO.getTipo(), paisId, competicionId);
        CamisetaRequestDTO camiseta = workFlowMapper.toCamisetaRequestDTO(altaCompletaRequestDTO, paisId, competicionId, equipoId);
        return camisetaService.guardar(camiseta);
    }

    /**
     * Busca un país por nombre. Si no existe, lo crea.
     * @param nombrePais
     * @return
     */
    private String findOrCreatePais(String nombrePais) {
        Optional<Pais> paisOptional = paisRepository.findByNombreIgnoreCase(nombrePais);
        if (paisOptional.isPresent()) {
            return paisOptional.get().getId();
        }
        PaisRequestDTO paisDTO = workFlowMapper.toPaisRequestDTO(nombrePais);
        PaisResponseDTO paisCreado = paisService.guardar(paisDTO);
        return paisCreado.getId();
    }

    /**
     *  Busca una competición por nombre. Si no existe, la crea.
     * @param nombreCompeticion
     * @param paisId
     * @return
     */
    private String findOrCreateCompeticion(String nombreCompeticion, String paisId) {
        Optional<Competicion> competicionOpcional = competicionRepository.findByNombreIgnoreCase(nombreCompeticion);
        if (competicionOpcional.isPresent()) {
            return competicionOpcional.get().getId();
        }
        CompeticionRequestDTO competicionDTO = workFlowMapper.toCompeticionRequestDTO(nombreCompeticion, paisId);
        CompeticionResponseDTO competicionCreada = competicionService.guardar(competicionDTO);
        return competicionCreada.getId();
    }

    /**
     * Busca un equipo por nombre y tipo. Si no existe, lo crea.
     * @param nombreEquipo
     * @param tipo
     * @param paisId
     * @param competicionId
     * @return
     */
    private String findOrCreateEquipo(String nombreEquipo, TipoEquipo tipo,  String paisId, String competicionId) {
        Optional<Equipo> equipoOpcional = equipoRepository.findByNombreIgnoreCase(nombreEquipo);
        if (equipoOpcional.isPresent()) {
            return equipoOpcional.get().getId();
        }
        EquipoRequestDTO equipoDTO = workFlowMapper.toEquipoRequestDTO(nombreEquipo, tipo, paisId, competicionId);
        EquipoResponseDTO equipoCreado = equipoService.guardar(equipoDTO);
        return equipoCreado.getId();
    }

}

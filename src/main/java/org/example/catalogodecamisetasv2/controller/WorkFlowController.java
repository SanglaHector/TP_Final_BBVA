package org.example.catalogodecamisetasv2.controller;

import jakarta.validation.Valid;
import org.example.catalogodecamisetasv2.dto.AltaCompletaRequestDTO;
import org.example.catalogodecamisetasv2.dto.CamisetaResponseDTO;
import org.example.catalogodecamisetasv2.service.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/api/workflow")
public class WorkFlowController {
    private final CamisetaWorkFlowService camisetaWorkFlowService;
    private final ExecutorService workflowExecutor;

    public WorkFlowController(CamisetaWorkFlowService camisetaWorkFlowService,
                             @Qualifier("workflowExecutor") ExecutorService workflowExecutor) {
        this.camisetaWorkFlowService = camisetaWorkFlowService;
        this.workflowExecutor = workflowExecutor;
    }

    /**
     * Método transaccional que crea un alta completa (País, Competición, Equipo y Camiseta).
     * CONCURRENTE: Procesa País y Competición en paralelo (son independientes
     * @param altaCompleta
     * @return
     */
    @PostMapping("/altaCompletaAsync")
    public ResponseEntity<CamisetaResponseDTO> altaCompletaAsync(@Valid @RequestBody AltaCompletaRequestDTO altaCompleta) {
        CamisetaResponseDTO camisetaCreada = camisetaWorkFlowService.guardarAltaCompletaAsync(altaCompleta);
        return ResponseEntity.ok(camisetaCreada);
    }

    /**
     * Método transaccional que crea un alta completa (País, Competición, Equipo y Camiseta).
     * SECUENCIAL: Procesa País, Competición, Equipo y Camiseta en orden
     * @param altaCompleta
     * @return
     */
    @PostMapping("/altaCompleta")
    public ResponseEntity<CamisetaResponseDTO> altaCompleta(@Valid @RequestBody AltaCompletaRequestDTO altaCompleta) {
        CamisetaResponseDTO camisetaCreada = camisetaWorkFlowService.guardarAltaCompleta(altaCompleta);
        return ResponseEntity.ok(camisetaCreada);
    }
    /**
     * Procesar MÚLTIPLES altas en PARALELO
     * Cada alta se ejecuta en un hilo diferente del pool
     * Ideal para importar lotes de camisetas
     *
     * Ejemplo: 3 altas se procesan casi simultáneamente, no secuencialmente
     */
    @PostMapping("/altasCompletas-paralelo")
    public ResponseEntity<List<CamisetaResponseDTO>> altasCompletasParalelo(
            @Valid @RequestBody List<AltaCompletaRequestDTO> altasCompletas) {

        // Crear un CompletableFuture por cada alta
        List<CompletableFuture<CamisetaResponseDTO>> futures = altasCompletas.stream()
                .map(alta -> CompletableFuture.supplyAsync(
                        () -> camisetaWorkFlowService.guardarAltaCompletaAsync(alta),
                        workflowExecutor
                ))
                .toList();

        // Esperar a que TODOS terminen y recopilar resultados
        List<CamisetaResponseDTO> resultados = futures.stream()
                .map(CompletableFuture::join)  // Bloquea hasta que termine
                .toList();

        return ResponseEntity.ok(resultados);
    }
}

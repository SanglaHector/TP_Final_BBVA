package org.example.catalogodecamisetasv2.controller;

import jakarta.validation.Valid;
import org.example.catalogodecamisetasv2.dto.CamisetaRequestDTO;
import org.example.catalogodecamisetasv2.dto.CamisetaResponseDTO;
import org.example.catalogodecamisetasv2.service.CamisetaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/camisetas")
public class CamisetaController {
    private final CamisetaService camisetaService;

    public CamisetaController(CamisetaService camisetaService) {
        this.camisetaService = camisetaService;
    }

    @GetMapping
    public ResponseEntity<Page<CamisetaResponseDTO>> listar(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<CamisetaResponseDTO> pagina = camisetaService.obtenerCamisetas(pageable);
        return ResponseEntity.ok(pagina);                                         // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamisetaResponseDTO> obtener(@PathVariable String id) {
        return camisetaService.obtenerCamisetaPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/buscar")
    public ResponseEntity<Page<CamisetaResponseDTO>>buscar(@ModelAttribute CamisetaRequestDTO filtros,
                                                     @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<CamisetaResponseDTO> pagina = camisetaService.buscarCamiseta(filtros, pageable);
        if (pagina.hasContent()) {
            return ResponseEntity.ok(pagina); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(Page.empty()); // 404 Not Found
        }
    }
    @GetMapping("/competicion/{competicionId}")
    public ResponseEntity<Page<CamisetaResponseDTO>> obtenerPorCompeticion(@PathVariable String competicionId,
                                                                            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<CamisetaResponseDTO> pagina = camisetaService.obtenerCamisetasPorCompeticion(competicionId, pageable);
        if (pagina.hasContent()) {
            return ResponseEntity.ok(pagina); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(Page.empty()); // 404 Not Found
        }
    }
    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<Page<CamisetaResponseDTO>> obtenerPorEquipo(@PathVariable String equipoId,
                                                                       @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<CamisetaResponseDTO> pagina = camisetaService.obtenerCamisetasPorEquipo(equipoId, pageable);
        if (pagina.hasContent()) {
            return ResponseEntity.ok(pagina); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(Page.empty()); // 404 Not Found
        }
    }
    @GetMapping("/pais/{paisId}")
    public ResponseEntity<Page<CamisetaResponseDTO>> obtenerPorPais(@PathVariable String paisId,
                                                                     @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<CamisetaResponseDTO> pagina = camisetaService.obtenerCamisetasPorPais(paisId, pageable);
        if (pagina.hasContent()) {
            return ResponseEntity.ok(pagina); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(Page.empty()); // 404 Not Found
        }
    }
    @PostMapping
    public ResponseEntity<CamisetaResponseDTO> crear(@Valid @RequestBody CamisetaRequestDTO camisetaDTO) {
        CamisetaResponseDTO camisetaCreada = camisetaService.guardar(camisetaDTO);
        return ResponseEntity.status(201).body(camisetaCreada); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable String id, @Valid @RequestBody CamisetaRequestDTO camisetaDTO) {
        CamisetaResponseDTO camisetaActualizada = camisetaService.actualizar(id, camisetaDTO);
        return ResponseEntity.ok(camisetaActualizada); // 200 OK
    }
    @PutMapping("/stock/{id}")
    public ResponseEntity<Object> actualizarStock(@PathVariable String id,@Valid @RequestBody Map<String, Integer> stockMap) {
        Integer stock = stockMap.get("stock");
        CamisetaResponseDTO camisetaActualizada = camisetaService.actualizarStock(id, stock);
        return ResponseEntity.ok(camisetaActualizada); // 200 OK
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable String id) {
        camisetaService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}



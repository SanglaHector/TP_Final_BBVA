package org.example.catalogodecamisetasv2.controller;

import jakarta.validation.Valid;
import org.example.catalogodecamisetasv2.dto.CompeticionRequestDTO;
import org.example.catalogodecamisetasv2.dto.CompeticionResponseDTO;
import org.example.catalogodecamisetasv2.service.CompeticionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/competiciones")
public class CompeticionController {
    private final CompeticionService competicionService;

    public CompeticionController(CompeticionService competicionService) {
        this.competicionService = competicionService;
    }

    @GetMapping
    public ResponseEntity<Page<CompeticionResponseDTO>> listar(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<CompeticionResponseDTO> pagina = competicionService.obtenerCompeticiones(pageable);
        return ResponseEntity.ok(pagina);                                         // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompeticionResponseDTO> obtener(@PathVariable String id) {
        return competicionService.obtenerCompeticionPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/buscar")
    public ResponseEntity<Page<CompeticionResponseDTO>> buscar(@ModelAttribute CompeticionRequestDTO filtros,
                                                                @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<CompeticionResponseDTO> pagina = competicionService.buscarCompeticion(filtros, pageable);
        if (pagina.hasContent()) {
            return ResponseEntity.ok(pagina); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    @PostMapping
    public ResponseEntity<CompeticionResponseDTO> crear(@Valid @RequestBody CompeticionRequestDTO competicionDTO) {
        CompeticionResponseDTO competicionCreada = competicionService.guardar(competicionDTO);
        return ResponseEntity.status(201).body(competicionCreada); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable String id,@Valid @RequestBody CompeticionRequestDTO competicionDTO) {
        CompeticionResponseDTO competicionActualizada = competicionService.actualizar(id, competicionDTO);
        return ResponseEntity.ok(competicionActualizada); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable String id) {
        competicionService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}


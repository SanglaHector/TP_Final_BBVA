package org.example.catalogodecamisetasv2.controller;

import jakarta.validation.Valid;
import org.example.catalogodecamisetasv2.dto.EquipoRequestDTO;
import org.example.catalogodecamisetasv2.dto.EquipoResponseDTO;
import org.example.catalogodecamisetasv2.service.EquipoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {
    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public ResponseEntity<Page<EquipoResponseDTO>> listar(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<EquipoResponseDTO> pagina = equipoService.obtenerEquipos(pageable);
        return ResponseEntity.ok(pagina);                                         // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> obtener(@PathVariable String id) {
        return equipoService.obtenerEquipoPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<EquipoResponseDTO>> buscar(@ModelAttribute EquipoRequestDTO filtros,
                                                          @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<EquipoResponseDTO> pagina = equipoService.buscarEquipo(filtros, pageable);
        if (pagina.hasContent()) {
            return ResponseEntity.ok(pagina); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    @PostMapping
    public ResponseEntity<EquipoResponseDTO> crear(@Valid @RequestBody EquipoRequestDTO equipoDTO) {
        EquipoResponseDTO equipoCreado = equipoService.guardar(equipoDTO);
        return ResponseEntity.status(201).body(equipoCreado); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable String id,@Valid @RequestBody EquipoRequestDTO equipoDTO) {
        EquipoResponseDTO equipoActualizado = equipoService.actualizar(id, equipoDTO);
        return ResponseEntity.ok(equipoActualizado); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable String id) {
        equipoService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}


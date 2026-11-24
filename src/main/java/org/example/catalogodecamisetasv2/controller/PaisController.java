package org.example.catalogodecamisetasv2.controller;

import jakarta.validation.Valid;
import org.example.catalogodecamisetasv2.dto.PaisRequestDTO;
import org.example.catalogodecamisetasv2.dto.PaisResponseDTO;
import org.example.catalogodecamisetasv2.service.PaisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paises")
public class PaisController {
    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    public ResponseEntity<Page<PaisResponseDTO>> listar(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<PaisResponseDTO> pagina = paisService.obtenerPaises(pageable);
        return ResponseEntity.ok(pagina);                                         // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisResponseDTO> obtener(@PathVariable String id) {
        return paisService.obtenerPaisPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/buscar")
    public ResponseEntity<Page<PaisResponseDTO>> buscar(
            @RequestParam(required = false) String nombre,
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<PaisResponseDTO> pagina = paisService.buscarPais(nombre, pageable);
        return ResponseEntity.ok(pagina); // 200 OK
    }

    @PostMapping
    public ResponseEntity<PaisResponseDTO> crear(@Valid @RequestBody PaisRequestDTO paisDTO) {
        PaisResponseDTO paisCreado = paisService.guardar(paisDTO);
        return ResponseEntity.status(201).body(paisCreado); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable String id,@Valid @RequestBody PaisRequestDTO paisDTO) {
        PaisResponseDTO paisActualizado = paisService.actualizar(id, paisDTO);
        return ResponseEntity.ok(paisActualizado); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable String id) {
        paisService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}


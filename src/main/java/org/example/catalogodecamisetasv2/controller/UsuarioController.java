package org.example.catalogodecamisetasv2.controller;

import jakarta.validation.Valid;
import org.example.catalogodecamisetasv2.dto.UsuarioCreateDTO;
import org.example.catalogodecamisetasv2.dto.UsuarioRequestDTO;
import org.example.catalogodecamisetasv2.dto.UsuarioResponseDTO;
import org.example.catalogodecamisetasv2.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para operaciones de Usuario.
 * Maneja las solicitudes HTTP relacionadas con usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para obtener todos los usuarios.
     * Devuelve nombre y rol de cada usuario.
     *
     * @param pageable Configuración de paginación
     * @return ResponseEntity con una página de UsuarioResponseDTO
     */
    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listar(
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<UsuarioResponseDTO> pagina = usuarioService.obtenerTodos(pageable);
        return ResponseEntity.ok(pagina); // 200 OK
    }

    /**
     * Endpoint para crear un nuevo usuario.
     * Recibe nombre, contraseña y rol del usuario.
     *
     * @param usuarioDTO DTO con los datos del usuario a crear
     * @return ResponseEntity con el UsuarioResponseDTO del usuario creado
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioCreateDTO usuarioDTO) {
        UsuarioResponseDTO usuarioCreado = usuarioService.crear(usuarioDTO);
        return ResponseEntity.status(201).body(usuarioCreado); // 201 Created
    }

    /**
     * Endpoint para actualizar un usuario existente.
     * Recibe nombre, contraseña y rol actualizados del usuario.
     *
     * @param id ID del usuario a actualizar
     * @param usuarioDTO DTO con los datos actualizados
     * @return ResponseEntity con el UsuarioResponseDTO del usuario actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(@PathVariable String id, @Valid @RequestBody UsuarioCreateDTO usuarioDTO) {
        UsuarioResponseDTO usuarioActualizado = usuarioService.actualizar(id, usuarioDTO);
        return ResponseEntity.ok(usuarioActualizado); // 200 OK
    }

    /**
     * Endpoint para eliminar un usuario.
     *
     * @param id ID del usuario a eliminar
     * @return ResponseEntity sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable String id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * Endpoint de login.
     * Recibe nombre y contraseña del usuario y devuelve si el login es correcto.
     *
     * @param usuarioDTO DTO con nombre y contraseña del usuario
     * @return ResponseEntity con un booleano indicando si el login es correcto
     */
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        try {
            boolean loginCorrecto = usuarioService.login(usuarioDTO);
            return ResponseEntity.ok(loginCorrecto); // 200 OK con true o false
        } catch (Exception e) {
            return ResponseEntity.ok(false); // 200 OK con false en caso de error
        }
    }
}

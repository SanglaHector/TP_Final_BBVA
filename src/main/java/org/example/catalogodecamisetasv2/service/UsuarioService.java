package org.example.catalogodecamisetasv2.service;

import lombok.RequiredArgsConstructor;
import org.example.catalogodecamisetasv2.dto.UsuarioCreateDTO;
import org.example.catalogodecamisetasv2.dto.UsuarioRequestDTO;
import org.example.catalogodecamisetasv2.dto.UsuarioResponseDTO;
import org.example.catalogodecamisetasv2.exception.UsuarioNotFoundException;
import org.example.catalogodecamisetasv2.mapper.UsuarioMapper;
import org.example.catalogodecamisetasv2.model.Usuario;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio de negocio para Usuario.
 * Maneja la lógica entre el controlador y el repositorio.
 * Realiza conversiones automáticas entre entidades y DTOs.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    /**
     * Convierte Page<Usuario> a Page<UsuarioResponseDTO>.
     * Evita duplicación del patrón .map(mapper::toDTO).
     *
     * @param pagina Página de usuarios
     * @return Página de DTOs
     */
    private Page<UsuarioResponseDTO> convertirPaginaADTO(Page<Usuario> pagina) {
        return pagina.map(usuarioMapper::toUsuarioResponseDTO);
    }

    /**
     * Obtiene todos los usuarios con paginación.
     *
     * @param pageable Configuración de paginación
     * @return Página de UsuarioResponseDTO
     */
    public Page<UsuarioResponseDTO> obtenerTodos(Pageable pageable) {
        return convertirPaginaADTO(usuarioRepository.findAll(pageable));
    }

    /**
     * Realiza el login de un usuario.
     * Verifica que el usuario existe y que la contraseña es correcta.
     *
     * @param requestDTO DTO con nombre y contraseña del usuario
     * @return true si el login es correcto, false en caso contrario
     * @throws UsuarioNotFoundException si el usuario no existe
     */
    public boolean login(UsuarioRequestDTO requestDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreIgnoreCase(requestDTO.getNombre());

        if (usuarioOpt.isEmpty()) {
            throw new UsuarioNotFoundException("El usuario con nombre " + requestDTO.getNombre() + " no existe");
        }

        Usuario usuario = usuarioOpt.get();
        // Verificar que la contraseña coincide
        return usuario.getClave().equals(requestDTO.getClave());
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario
     * @return Optional con UsuarioResponseDTO si existe
     */
    public Optional<UsuarioResponseDTO> obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toUsuarioResponseDTO);
    }

    /**
     * Obtiene un usuario por su nombre.
     *
     * @param nombre Nombre del usuario
     * @return Optional con UsuarioResponseDTO si existe
     */
    public Optional<UsuarioResponseDTO> obtenerUsuarioPorNombre(String nombre) {
        return usuarioRepository.findByNombreIgnoreCase(nombre)
                .map(usuarioMapper::toUsuarioResponseDTO);
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param createDTO DTO con los datos del usuario a crear
     * @return UsuarioResponseDTO del usuario creado
     */
    public UsuarioResponseDTO crear(UsuarioCreateDTO createDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(createDTO.getNombre());
        usuario.setClave(createDTO.getClave());
        usuario.setRol(createDTO.getRol());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toUsuarioResponseDTO(usuarioGuardado);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id ID del usuario a actualizar
     * @param updateDTO DTO con los datos actualizados
     * @return UsuarioResponseDTO del usuario actualizado
     * @throws UsuarioNotFoundException si el usuario no existe
     */
    public UsuarioResponseDTO actualizar(String id, UsuarioCreateDTO updateDTO) {
        // Validar que el usuario existe
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new UsuarioNotFoundException("El usuario con ID " + id + " no existe");
        }

        Usuario usuario = usuarioOpt.get();
        // Actualizar campos
        usuario.setNombre(updateDTO.getNombre());
        usuario.setClave(updateDTO.getClave());
        usuario.setRol(updateDTO.getRol());

        // Guardar cambios
        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        // Convertir a DTO y retornar
        return usuarioMapper.toUsuarioResponseDTO(usuarioActualizado);
    }

    /**
     * Crea un nuevo usuario (sobrecarga).
     *
     * @param usuario Entidad Usuario a crear
     * @return UsuarioResponseDTO del usuario creado
     */
    public UsuarioResponseDTO guardar(Usuario usuario) {
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toUsuarioResponseDTO(usuarioGuardado);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar
     * @throws UsuarioNotFoundException si el usuario no existe
     */
    public void eliminar(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("El usuario con ID " + id + " no existe");
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Verifica si existe un usuario con un nombre específico.
     *
     * @param nombre Nombre del usuario a buscar
     * @return true si existe, false si no existe
     */
    public boolean existePorNombre(String nombre) {
        return usuarioRepository.findByNombreIgnoreCase(nombre).isPresent();
    }
}

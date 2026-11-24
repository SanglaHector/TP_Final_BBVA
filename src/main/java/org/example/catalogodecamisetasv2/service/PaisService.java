package org.example.catalogodecamisetasv2.service;

import lombok.RequiredArgsConstructor;
import org.example.catalogodecamisetasv2.dto.PaisRequestDTO;
import org.example.catalogodecamisetasv2.dto.PaisResponseDTO;
import org.example.catalogodecamisetasv2.exception.PaisNotFoundException;
import org.example.catalogodecamisetasv2.mapper.PaisMapper;
import org.example.catalogodecamisetasv2.model.Pais;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.PaisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio de negocio para País.
 * Maneja la lógica entre el controlador y el repositorio.
 * Realiza conversiones automáticas entre entidades y DTOs.
 */
@Service
@RequiredArgsConstructor
public class PaisService {

    private final PaisRepository paisRepository;
    private final PaisMapper paisMapper;

    /**
     * Método helper para convertir Page<Pais> a Page<PaisResponseDTO>.
     * Evita duplicación del patrón .map(mapper::toDTO).
     *
     * @param pagina Página de países
     * @return Página de DTOs
     */
    private Page<PaisResponseDTO> convertirPaginaADTO(Page<Pais> pagina) {
        return pagina.map(paisMapper::toPaisResponseDTO);
    }

    /**
     * Obtiene todos los países con paginación.
     *
     * @param pageable Configuración de paginación
     * @return Página de PaisResponseDTO
     */
    public Page<PaisResponseDTO> obtenerPaises(Pageable pageable) {
        return convertirPaginaADTO(paisRepository.findAll(pageable));
    }

    /**
     * Obtiene un país por su ID.
     *
     * @param id ID del país
     * @return Optional con PaisResponseDTO si existe
     */
    public Optional<PaisResponseDTO> obtenerPaisPorId(String id) {
        return paisRepository.findById(id)
                .map(paisMapper::toPaisResponseDTO);
    }

    /**
     * Obtiene países por nombre con paginación.
     * @param nombre Nombre del país a buscar
     * @param pageable Configuración de paginación
     */
    public Page<PaisResponseDTO> buscarPais(String nombre, Pageable pageable) {
        if (nombre == null || nombre.isBlank()) {
            return convertirPaginaADTO(paisRepository.findAll(pageable));
        } else {
            return convertirPaginaADTO(paisRepository.findByNombreIgnoreCase(nombre, pageable));
        }
    }
    /**
     * Crea un nuevo país.
     *
     * @param requestDTO DTO con los datos del país a crear
     * @return PaisResponseDTO del país creado
     */
    public PaisResponseDTO guardar(PaisRequestDTO requestDTO) {
        // Crear nuevo país desde el DTO
        Pais pais = new Pais();
        pais.setNombre(requestDTO.getNombre());

        // Guardar en repositorio
        Pais paisGuardado = paisRepository.save(pais);

        // Convertir a DTO y retornar
        return paisMapper.toPaisResponseDTO(paisGuardado);
    }

    /**
     * Actualiza un país existente.
     *
     * @param id ID del país a actualizar
     * @param requestDTO DTO con los datos actualizados
     * @return PaisResponseDTO del país actualizado
     * @throws PaisNotFoundException si el país no existe
     */
    public PaisResponseDTO actualizar(String id, PaisRequestDTO requestDTO) {
        // Validar que el país existe
        Optional<Pais> paisOpt = paisRepository.findById(id);
        if (paisOpt.isEmpty()) {
            throw new PaisNotFoundException("El país con ID " + id + " no existe");
        }

        // Actualizar campos
        Pais pais = paisOpt.get();
        pais.setNombre(requestDTO.getNombre());

        // Guardar cambios
        Pais paisActualizado = paisRepository.save(pais);

        // Convertir a DTO y retornar
        return paisMapper.toPaisResponseDTO(paisActualizado);
    }

    /**
     * Elimina un país por su ID.
     *
     * @param id ID del país a eliminar
     * @throws PaisNotFoundException si el país no existe
     */
    public void eliminar(String id) {
        // Validar que el país existe
        if (!paisRepository.existsById(id)) {
            throw new PaisNotFoundException("El país con ID " + id + " no existe");
        }

        // Eliminar país
        paisRepository.deleteById(id);
    }

    /**
     * Obtiene el total de países en la base de datos.
     *
     * @return Total de países
     */
    public long obtenerTotal() {
        return paisRepository.count();
    }
    /**
     * Busca paises por nombre
     *
     * @param nombre Nombre del país a buscar
     * @param pageable Configuración de paginación
     * @return Página de PaisResponseDTO que coinciden con el nombre
     */
    public Page<PaisResponseDTO> buscarPorNombre(String nombre, Pageable pageable) {
        return convertirPaginaADTO(paisRepository.findByNombreIgnoreCase(nombre, pageable));
    }
    /**
     * Verifica si el pais existe por nombre
     * @Param nombre Nombre del país a buscar
     * @return true si existe, false si no existe
     */
    public Boolean existePorNombre(String nombre) {
        return paisRepository.findByNombreIgnoreCase(nombre).isPresent();
    }
}


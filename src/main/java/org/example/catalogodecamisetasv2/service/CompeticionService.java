package org.example.catalogodecamisetasv2.service;

import lombok.RequiredArgsConstructor;
import org.example.catalogodecamisetasv2.dto.CompeticionRequestDTO;
import org.example.catalogodecamisetasv2.dto.CompeticionResponseDTO;
import org.example.catalogodecamisetasv2.exception.CompeticionNotFoundException;
import org.example.catalogodecamisetasv2.exception.PaisNotFoundException;
import org.example.catalogodecamisetasv2.mapper.CompeticionMapper;
import org.example.catalogodecamisetasv2.model.Competicion;
import org.example.catalogodecamisetasv2.model.Pais;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.CompeticionRepository;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.PaisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio de negocio para Competición.
 * Maneja la lógica entre el controlador y el repositorio.
 * Realiza conversiones automáticas entre entidades y DTOs.
 */
@Service
@RequiredArgsConstructor
public class CompeticionService {

    private final CompeticionRepository competicionRepository;
    private final PaisRepository paisRepository;
    private final CompeticionMapper competicionMapper;

    /**
     * Método helper para convertir Page<Competicion> a Page<CompeticionResponseDTO>.
     * Evita duplicación del patrón .map(mapper::toDTO).
     *
     * @param pagina Página de competiciones
     * @return Página de DTOs
     */
    private Page<CompeticionResponseDTO> convertirPaginaADTO(Page<Competicion> pagina) {
        return pagina.map(competicionMapper::toCompeticionResponseDTO);
    }

    /**
     * Obtiene todas las competiciones con paginación.
     *
     * @param pageable Configuración de paginación
     * @return Página de CompeticionResponseDTO
     */
    public Page<CompeticionResponseDTO> obtenerCompeticiones(Pageable pageable) {
        return convertirPaginaADTO(competicionRepository.findAll(pageable));
    }

    /**
     * Obtiene una competición por su ID.
     *
     * @param id ID de la competición
     * @return Optional con CompeticionResponseDTO si existe
     */
    public Optional<CompeticionResponseDTO> obtenerCompeticionPorId(String id) {
        return competicionRepository.findById(id)
                .map(competicionMapper::toCompeticionResponseDTO);
    }

    /**
     * Crea una nueva competición.
     *
     * @param requestDTO DTO con los datos de la competición a crear
     * @return CompeticionResponseDTO de la competición creada
     * @throws PaisNotFoundException si el país no existe
     */
    public CompeticionResponseDTO guardar(CompeticionRequestDTO requestDTO) {
        // Validar que el país existe
        Optional<Pais> pais = paisRepository.findById(requestDTO.getPaisId());
        if (pais.isEmpty()) {
            throw new PaisNotFoundException("El país con ID " + requestDTO.getPaisId() + " no existe");
        }

        // Crear nueva competición desde el DTO
        Competicion competicion = new Competicion();
        competicion.setNombre(requestDTO.getNombre());
        competicion.setPais(pais.get());

        // Guardar en repositorio
        Competicion competicionGuardada = competicionRepository.save(competicion);

        // Convertir a DTO y retornar
        return competicionMapper.toCompeticionResponseDTO(competicionGuardada);
    }

    /**
     * Actualiza una competición existente.
     *
     * @param id ID de la competición a actualizar
     * @param requestDTO DTO con los datos actualizados
     * @return CompeticionResponseDTO de la competición actualizada
     * @throws CompeticionNotFoundException | PaisNotFoundException | si la competición o el país no existen
     */
    public CompeticionResponseDTO actualizar(String id, CompeticionRequestDTO requestDTO) {
        // Validar que la competición existe
        Optional<Competicion> competicionOpt = competicionRepository.findById(id);
        if (competicionOpt.isEmpty()) {
            throw new CompeticionNotFoundException("La competición con ID " + id + " no existe");
        }

        // Validar que el país existe
        Optional<Pais> paisOpt = paisRepository.findById(requestDTO.getPaisId());
        if (paisOpt.isEmpty()) {
            throw new PaisNotFoundException("El país con ID " + requestDTO.getPaisId() + " no existe");
        }

        // Actualizar campos
        Competicion competicion = competicionOpt.get();
        competicion.setNombre(requestDTO.getNombre());
        competicion.setPais(paisOpt.get());

        // Guardar cambios
        Competicion competicionActualizada = competicionRepository.save(competicion);

        // Convertir a DTO y retornar
        return competicionMapper.toCompeticionResponseDTO(competicionActualizada);
    }

    /**
     * Elimina una competición por su ID.
     *
     * @param id ID de la competición a eliminar
     * @throws CompeticionNotFoundException si la competición no existe
     */
    public void eliminar(String id) {
        // Validar que la competición existe
        if (!competicionRepository.existsById(id)) {
            throw new CompeticionNotFoundException("La competición con ID " + id + " no existe");
        }

        // Eliminar competición
        competicionRepository.deleteById(id);
    }

    /**
     * Obtiene el total de competiciones en la base de datos.
     *
     * @return Total de competiciones
     */
    public long obtenerTotal() {
        return competicionRepository.count();
    }
    /**
     * Obtiene todas las competiciones por pais
     *
     * @return Pagina de CompeticionResponseDTO
     */
    public Page<CompeticionResponseDTO> obtenerCompeticionesPorPais(String paisId, Pageable pageable) {
        return convertirPaginaADTO(competicionRepository.findByPaisId(paisId, pageable));
    }
    /**
     * Obtiene todas las competiciones por nombre
     *
     * @return Pagina de CompeticionResponseDTO
     */
    public Page<CompeticionResponseDTO> obtenerCompeticionesPorNombre(String nombre, Pageable pageable) {
        return convertirPaginaADTO(competicionRepository.findByNombreIgnoreCase(nombre, pageable));
    }
    /**
     * Busca Competicion por los filtros proporcionados.
     * @param filtros DTO con los criterios de búsqueda (nombre, paisId)
     * @param pageable Configuración de paginación
     * @return Página de CompeticionResponseDTO filtradas según los criterios
     */
    public Page<CompeticionResponseDTO> buscarCompeticion(CompeticionRequestDTO filtros, Pageable pageable) {
        return convertirPaginaADTO(competicionRepository.buscarConFiltros(filtros, pageable));
    }

    /**
     * Verifico si existe la competicion por nombre
     * @param nombre Nombre de la competición a buscar
     * @return true si existe, false si no existe
     */
    public Boolean existePorNombre(String nombre) {
        return competicionRepository.findByNombreIgnoreCase(nombre).isPresent();
    }

}


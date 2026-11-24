package org.example.catalogodecamisetasv2.service;

import lombok.RequiredArgsConstructor;
import org.example.catalogodecamisetasv2.dto.EquipoRequestDTO;
import org.example.catalogodecamisetasv2.dto.EquipoResponseDTO;
import org.example.catalogodecamisetasv2.exception.CompeticionNotFoundException;
import org.example.catalogodecamisetasv2.exception.EquipoNotFoundException;
import org.example.catalogodecamisetasv2.exception.PaisNotFoundException;
import org.example.catalogodecamisetasv2.mapper.EquipoMapper;
import org.example.catalogodecamisetasv2.model.Competicion;
import org.example.catalogodecamisetasv2.model.Equipo;
import org.example.catalogodecamisetasv2.model.Pais;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.CompeticionRepository;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.EquipoRepository;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.PaisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio de negocio para Equipo.
 * Maneja la lógica entre el controlador y el repositorio.
 * Realiza conversiones automáticas entre entidades y DTOs.
 */
@Service
@RequiredArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final PaisRepository paisRepository;
    private final CompeticionRepository competicionRepository;
    private final EquipoMapper equipoMapper;

    /**
     * Método helper para convertir Page<Equipo> a Page<EquipoResponseDTO>.
     * Evita duplicación del patrón .map(mapper::toDTO).
     *
     * @param pagina Página de equipos
     * @return Página de DTOs
     */
    private Page<EquipoResponseDTO> convertirPaginaADTO(Page<Equipo> pagina) {
        return pagina.map(equipoMapper::toEquipoResponseDTO);
    }

    /**
     * Obtiene todos los equipos con paginación.
     *
     * @param pageable Configuración de paginación
     * @return Página de EquipoResponseDTO
     */
    public Page<EquipoResponseDTO> obtenerEquipos(Pageable pageable) {
        return convertirPaginaADTO(equipoRepository.findAll(pageable));
    }

    /**
     * Obtiene un equipo por su ID.
     *
     * @param id ID del equipo
     * @return Optional con EquipoResponseDTO si existe
     */
    public Optional<EquipoResponseDTO> obtenerEquipoPorId(String id) {
        return equipoRepository.findById(id)
                .map(equipoMapper::toEquipoResponseDTO);
    }

    /**
     * Crea un nuevo equipo.
     *
     * @param requestDTO DTO con los datos del equipo a crear
     * @return EquipoResponseDTO del equipo creado
     * @throws PaisNotFoundException | CompeticionNotFoundException si el país o competición no existen
     */
    public EquipoResponseDTO guardar(EquipoRequestDTO requestDTO) {
        // Validar que el país existe
        Optional<Pais> pais = paisRepository.findById(requestDTO.getPaisId());
        if (pais.isEmpty()) {
            throw new PaisNotFoundException("El país con ID " + requestDTO.getPaisId() + " no existe");
        }

        // Validar que la competición existe
        Optional<Competicion> competicion = competicionRepository.findById(requestDTO.getCompeticionId());
        if (competicion.isEmpty()) {
            throw new CompeticionNotFoundException("La competición con ID " + requestDTO.getCompeticionId() + " no existe");
        }

        // Crear nuevo equipo desde el DTO
        Equipo equipo = new Equipo();
        equipo.setNombre(requestDTO.getNombre());
        equipo.setTipo(requestDTO.getTipo());
        equipo.setPais(pais.get());
        equipo.setCompeticion(competicion.get());

        // Guardar en repositorio
        Equipo equipoGuardado = equipoRepository.save(equipo);

        // Convertir a DTO y retornar
        return equipoMapper.toEquipoResponseDTO(equipoGuardado);
    }

    /**
     * Actualiza un equipo existente.
     *
     * @param id ID del equipo a actualizar
     * @param requestDTO DTO con los datos actualizados
     * @return EquipoResponseDTO del equipo actualizado
     * @throws EquipoNotFoundException | PaisNotFoundException | CompeticionNotFoundException si el equipo, país o competición no existen
     */
    public EquipoResponseDTO actualizar(String id, EquipoRequestDTO requestDTO) {
        // Validar que el equipo existe
        Optional<Equipo> equipoOpt = equipoRepository.findById(id);
        if (equipoOpt.isEmpty()) {
            throw new EquipoNotFoundException("El equipo con ID " + id + " no existe");
        }

        // Validar que el país existe
        Optional<Pais> paisOpt = paisRepository.findById(requestDTO.getPaisId());
        if (paisOpt.isEmpty()) {
            throw new PaisNotFoundException("El país con ID " + requestDTO.getPaisId() + " no existe");
        }

        // Validar que la competición existe
        Optional<Competicion> competicionOpt = competicionRepository.findById(requestDTO.getCompeticionId());
        if (competicionOpt.isEmpty()) {
            throw new CompeticionNotFoundException("La competición con ID " + requestDTO.getCompeticionId() + " no existe");
        }

        // Actualizar campos
        Equipo equipo = equipoOpt.get();
        equipo.setNombre(requestDTO.getNombre());
        equipo.setTipo(requestDTO.getTipo());
        equipo.setPais(paisOpt.get());
        equipo.setCompeticion(competicionOpt.get());

        // Guardar cambios
        Equipo equipoActualizado = equipoRepository.save(equipo);

        // Convertir a DTO y retornar
        return equipoMapper.toEquipoResponseDTO(equipoActualizado);
    }

    /**
     * Elimina un equipo por su ID.
     *
     * @param id ID del equipo a eliminar
     * @throws EquipoNotFoundException si el equipo no existe
     */
    public void eliminar(String id) {
        // Validar que el equipo existe
        if (!equipoRepository.existsById(id)) {
            throw new EquipoNotFoundException("El equipo con ID " + id + " no existe");
        }

        // Eliminar equipo
        equipoRepository.deleteById(id);
    }

    /**
     * Obtiene el total de equipos en la base de datos.
     *
     * @return Total de equipos
     */
    public long obtenerTotal() {
        return equipoRepository.count();
    }
    /**
     * Busca equipos por ID de pais
     *
     * @param paisID   Nombre del equipo a buscar
     * @param pageable Configuración de paginación
     * @return Página de EquipoResponseDTO que coinciden con el idPais
     */
    public Page<EquipoResponseDTO> buscarPorPaisId(String paisID, Pageable pageable) {
        return convertirPaginaADTO(equipoRepository.findByPaisId(paisID, pageable));
    }
    /**
     * Busca equipos por ID de competicion
     *
     * @param competicionID   Nombre del equipo a buscar
     * @param pageable Configuración de paginación
     * @return Página de EquipoResponseDTO que coinciden con el idCompeticion
     */
    public Page<EquipoResponseDTO> buscarPorCompeticionId(String competicionID, Pageable pageable) {
        return convertirPaginaADTO(equipoRepository.findByCompeticionId(competicionID, pageable));
    }
    /**
     * Busca equipos por nombre
     *
     * @param nombre   Nombre del equipo a buscar
     * @param pageable Configuración de paginación
     * @return Página de EquipoResponseDTO que coinciden con el nombre
     */
    public Page<EquipoResponseDTO> buscarPorNombre(String nombre, Pageable pageable) {
        return convertirPaginaADTO(equipoRepository.findByNombreIgnoreCase(nombre, pageable));
    }
    /**
     * Busca equipos por los filtros proporcionados.
     * @param filtros DTO con los criterios de búsqueda (nombre, paisId, competicionId)
     * @param pageable Configuración de paginación
     * @return Página de EquipoResponseDTO filtradas según los criterios
     */
    public Page<EquipoResponseDTO> buscarEquipo(EquipoRequestDTO filtros, Pageable pageable) {
        return convertirPaginaADTO(equipoRepository.buscarConFiltros(filtros, pageable));
    }
    /**
     * Verifico si existe el equipo por nombre
     * @param nombre Nombre del equipo a buscar
     * @return true si existe, false si no existe
     */
    Boolean existePorNombre(String nombre) {
        return equipoRepository.findByNombreIgnoreCase(nombre).isPresent();
    }
}


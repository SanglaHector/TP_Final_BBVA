package org.example.catalogodecamisetasv2.service;


import lombok.RequiredArgsConstructor;
import org.example.catalogodecamisetasv2.dto.CamisetaRequestDTO;
import org.example.catalogodecamisetasv2.dto.CamisetaResponseDTO;
import org.example.catalogodecamisetasv2.exception.CamisetaNotFoundException;
import org.example.catalogodecamisetasv2.exception.DatosInvalidosException;
import org.example.catalogodecamisetasv2.exception.EquipoNotFoundException;
import org.example.catalogodecamisetasv2.mapper.CamisetaMapper;
import org.example.catalogodecamisetasv2.model.Camiseta;
import org.example.catalogodecamisetasv2.model.Equipo;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.CamisetaRepository;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.EquipoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Servicio de negocio para Camiseta.
 * Maneja la lógica entre el controlador y el repositorio.
 * Realiza conversiones automáticas entre entidades y DTOs.
 */
@Service
@RequiredArgsConstructor
public class CamisetaService {

    private final CamisetaRepository camisetaRepository;
    private final EquipoRepository equipoRepository;
    private final CamisetaMapper camisetaMapper;

    /**
     * Método helper para convertir Page<Camiseta> a Page<CamisetaResponseDTO>.
     * Evita duplicación del patrón .map(mapper::toDTO).
     *
     * @param pagina Página de camisetas
     * @return Página de DTOs
     */
    private Page<CamisetaResponseDTO> convertirPaginaADTO(Page<Camiseta> pagina) {
        return pagina.map(camisetaMapper::toCamisetaResponseDTO);
    }

    /**
     * Obtiene todas las camisetas con paginación.
     *
     * @param pageable Configuración de paginación
     * @return Página de CamisetaResponseDTO
     */
    public Page<CamisetaResponseDTO> obtenerCamisetas(Pageable pageable) {
        return convertirPaginaADTO(camisetaRepository.findAll(pageable));
    }

    /**
     * Obtiene una camiseta por su ID.
     *
     * @param id ID de la camiseta
     * @return Optional con CamisetaResponseDTO si existe
     */
    public Optional<CamisetaResponseDTO> obtenerCamisetaPorId(String id) {
        return camisetaRepository.findById(id)
                .map(camisetaMapper::toCamisetaResponseDTO);
    }
    /**
     * Crea una nueva camiseta.
     *
     * @param requestDTO DTO con los datos de la camiseta a crear
     * @return CamisetaResponseDTO de la camiseta creada
     * @throws DatosInvalidosException si el equipo no existe
     */
    public CamisetaResponseDTO guardar(CamisetaRequestDTO requestDTO) {
        // Validar que el equipo existe
        Optional<Equipo> equipo = equipoRepository.findById(requestDTO.getEquipoId());
        if (equipo.isEmpty()) {
            throw new DatosInvalidosException("El equipo con ID " + requestDTO.getEquipoId() + " no existe");
        }

        // Crear nueva camiseta desde el DTO
        Camiseta camiseta = new Camiseta();
        camiseta.setEquipo(equipo.get());
        camiseta.setTemporada(requestDTO.getTemporada());
        camiseta.setPrecio(requestDTO.getPrecio());
        camiseta.setTalle(requestDTO.getTalle());
        camiseta.setStock(requestDTO.getStock());
        camiseta.setMarca(requestDTO.getMarca());
        camiseta.setEsRetro(requestDTO.getEsRetro() != null ? requestDTO.getEsRetro() : false);

        // Guardar en repositorio
        Camiseta camisetaGuardada = camisetaRepository.save(camiseta);

        // Convertir a DTO y retornar
        return camisetaMapper.toCamisetaResponseDTO(camisetaGuardada);
    }

    /**
     * Actualiza una camiseta existente.
     *
     * @param id ID de la camiseta a actualizar
     * @param requestDTO DTO con los datos actualizados
     * @return CamisetaResponseDTO de la camiseta actualizada
     * @throws CamisetaNotFoundException si la camiseta no existe
     * @throws DatosInvalidosException si el equipo no existe
     */
    public CamisetaResponseDTO actualizar(String id, CamisetaRequestDTO requestDTO) {
        // Validar que la camiseta existe
        Optional<Camiseta> camisetaOpt = camisetaRepository.findById(id);
        if (camisetaOpt.isEmpty()) {
            throw new CamisetaNotFoundException("La camiseta con ID " + id + " no existe");
        }

        // Validar que el equipo existe
        Optional<Equipo> equipoOpt = equipoRepository.findById(requestDTO.getEquipoId());
        if (equipoOpt.isEmpty()) {
            throw new EquipoNotFoundException("El equipo con ID " + requestDTO.getEquipoId() + " no existe");
//            throw new DatosInvalidosException("El equipo con ID " + requestDTO.getEquipoId() + " no existe");
        }

        // Actualizar campos
        Camiseta camiseta = camisetaOpt.get();
        camiseta.setEquipo(equipoOpt.get());
        camiseta.setTemporada(requestDTO.getTemporada());
        camiseta.setPrecio(requestDTO.getPrecio());
        camiseta.setTalle(requestDTO.getTalle());
        camiseta.setStock(requestDTO.getStock());
        camiseta.setMarca(requestDTO.getMarca());
        camiseta.setEsRetro(requestDTO.getEsRetro() != null ? requestDTO.getEsRetro() : false);

        // Guardar cambios
        Camiseta camisetaActualizada = camisetaRepository.save(camiseta);

        // Convertir a DTO y retornar
        return camisetaMapper.toCamisetaResponseDTO(camisetaActualizada);
    }
    /**
     * Actualiza el stock de una camiseta existente.
     *
     * @param id    ID de la camiseta a actualizar
     * @param stock Nuevo valor de stock
     * @return CamisetaResponseDTO de la camiseta actualizada
     * @throws CamisetaNotFoundException si la camiseta no existe
     * @throws DatosInvalidosException si el stock es negativo
     */
    public CamisetaResponseDTO actualizarStock(String id, int stock) {
        // Validar que la camiseta existe
        Optional<Camiseta> camisetaOpt = camisetaRepository.findById(id);
        if (camisetaOpt.isEmpty()) {
            throw new CamisetaNotFoundException("La camiseta con ID " + id + " no existe");
        }
        // Validar que el stock no sea negativo
        if(stock < 0) {
            throw new DatosInvalidosException("El stock no puede ser negativo");
        }
        // Actualizar stock
        Camiseta camiseta = camisetaOpt.get();
        camiseta.setStock(stock);
        // Guardar cambios
        Camiseta camisetaActualizada = camisetaRepository.save(camiseta);
        // Convertir a DTO y retornar
        return camisetaMapper.toCamisetaResponseDTO(camisetaActualizada);
    }
    /**
     * Elimina una camiseta por su ID.
     *
     * @param id ID de la camiseta a eliminar
     * @throws CamisetaNotFoundException si la camiseta no existe
     */
    public void eliminar(String id) {
        // Validar que la camiseta existe
        if (!camisetaRepository.existsById(id)) {
            throw new CamisetaNotFoundException("La camiseta con ID " + id + " no existe");
        }

        // Eliminar camiseta
        camisetaRepository.deleteById(id);
    }

    /**
     * Busca camisetas según los filtros proporcionados.
     * @param filtros
     * @param pageable
     * @return Página de CamisetaResponseDTO filtradas según los criterios
     */
    public Page<CamisetaResponseDTO> buscarCamiseta(CamisetaRequestDTO filtros, Pageable pageable) {
        return convertirPaginaADTO(camisetaRepository.buscarConFiltros(filtros, pageable));
    }
    /**
     *
     * @param competicionId
     * @param pageable
     * @return Página de CamisetaResponseDTO filtradas por competición
     */
    public Page<CamisetaResponseDTO> obtenerCamisetasPorCompeticion(String competicionId, Pageable pageable) {
        return convertirPaginaADTO(camisetaRepository.findByEquipoCompeticionId(competicionId, pageable));
    }
    /**
     * Obtiene todas las camisetas de un equipo específico.
     * @param equipoId
     * @param pageable
     * @return Página de CamisetaResponseDTO filtradas por equipo
     */
    public Page<CamisetaResponseDTO> obtenerCamisetasPorEquipo(String equipoId, Pageable pageable) {
        return convertirPaginaADTO(camisetaRepository.findByEquipoId(equipoId, pageable));
    }
    /**
     * Obtiene todas las camisetas de un país específico.
     * Busca el país en los equipos asociados a cada camiseta.
     *
     * @param paisId ID del país
     * @param pageable Configuración de paginación
     * @return Página de CamisetaResponseDTO filtradas por país
     */
    public Page<CamisetaResponseDTO> obtenerCamisetasPorPais(String paisId, Pageable pageable) {
        return convertirPaginaADTO(camisetaRepository.findByEquipoPaisId(paisId, pageable));
    }
    /**
     * Obtiene el total de camisetas en la base de datos.
     *
     * @return Total de camisetas
     */
    public long obtenerTotal() {
        return camisetaRepository.count();
    }
}

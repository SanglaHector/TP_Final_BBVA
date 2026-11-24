package org.example.catalogodecamisetasv2.repository.RepositoryInterfaces;

import org.example.catalogodecamisetasv2.model.Camiseta;
import org.example.catalogodecamisetasv2.model.Talle;
import org.example.catalogodecamisetasv2.repository.RepositoryCustom.CamisetaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Camiseta usando MongoDB.
 * Extiende MongoRepository para obtener operaciones CRUD básicas.
 * Implementa CamisetaRepositoryCustom para búsquedas complejas y filtros dinámicos.
 *
 * Métodos heredados automáticamente de MongoRepository:
 * - save(), saveAll(), findById(), findAll(), count(), deleteById(), delete(), deleteAll(), etc.
 */
@Repository
public interface CamisetaRepository extends MongoRepository<Camiseta, String>, CamisetaRepositoryCustom {

    // --- BÚSQUEDAS POR CAMPO ÚNICO ---

    /**
     * Busca camisetas por temporada de forma case-insensitive.
     *
     * @param temporada Temporada a buscar
     * @param pageable Configuración de paginación
     * @return Página de camisetas encontradas
     */
    @Query("{ 'temporada': { $regex: ?0, $options: 'i' } }")
    Page<Camiseta> findByTemporadaIgnoreCase(String temporada, Pageable pageable);

    /**
     * Busca camisetas por marca de forma case-insensitive.
     *
     * @param marca Marca a buscar
     * @param pageable Configuración de paginación
     * @return Página de camisetas encontradas
     */
    @Query("{ 'marca': { $regex: ?0, $options: 'i' } }")
    Page<Camiseta> findByMarcaIgnoreCase(String marca, Pageable pageable);

    /**
     * Busca camisetas por talle.
     *
     * @param talle Talle a buscar
     * @param pageable Configuración de paginación
     * @return Página de camisetas encontradas
     */
    Page<Camiseta> findByTalle(Talle talle, Pageable pageable);

    /**
     * Busca camisetas por precio exacto.
     *
     * @param precio Precio a buscar
     * @param pageable Configuración de paginación
     * @return Página de camisetas encontradas
     */
    Page<Camiseta> findByPrecio(Double precio, Pageable pageable);

    /**
     * Busca camisetas por categoría de retro.
     *
     * @param esRetro Indicador si es retro o no
     * @param pageable Configuración de paginación
     * @return Página de camisetas encontradas
     */
    Page<Camiseta> findByEsRetro(Boolean esRetro, Pageable pageable);

    // --- BÚSQUEDAS POR RELACIÓN (EQUIPO, COMPETICIÓN, PAÍS) ---

    /**
     * Busca camisetas por ID de Equipo.
     *
     * @param equipoId ID del equipo
     * @param pageable Configuración de paginación
     * @return Página de camisetas asociadas al equipo
     */
    @Query("{ 'equipo': ?0 }")
    Page<Camiseta> findByEquipoId(String equipoId, Pageable pageable);

    /**
     * Verifica si existe una camiseta con un equipo específico.
     *
     * @param equipoId ID del equipo
     * @return true si existe al menos una camiseta con ese equipo, false en caso contrario
     */
    @Query("{ 'equipo': ?0 }")
    boolean existsByEquipoId(String equipoId);

    /**
     * Busca camisetas por ID de Competición (a través del equipo).
     * Este método requiere un join con la colección de equipos.
     * NOTA: Esta implementación usa agregación MongoDB que debe ser manejada en la implementación custom.
     *
     * @param competicionId ID de la competición
     * @param pageable Configuración de paginación
     * @return Página de camisetas asociadas a la competición
     */
    // Este método será manejado por CamisetaRepositoryImpl con agregación
    Page<Camiseta> findByEquipoCompeticionId(String competicionId, Pageable pageable);

    /**
     * Busca camisetas por ID de País (a través del equipo).
     * Este método requiere un join con la colección de equipos.
     * NOTA: Esta implementación usa agregación MongoDB que debe ser manejada en la implementación custom.
     *
     * @param paisId ID del país
     * @param pageable Configuración de paginación
     * @return Página de camisetas asociadas al país
     */
    // Este método será manejado por CamisetaRepositoryImpl con agregación
    Page<Camiseta> findByEquipoPaisId(String paisId, Pageable pageable);
}
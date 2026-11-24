package org.example.catalogodecamisetasv2.repository.RepositoryInterfaces;

import org.example.catalogodecamisetasv2.model.Equipo;
import org.example.catalogodecamisetasv2.repository.RepositoryCustom.EquipoRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Equipo usando MongoDB.
 * Extiende MongoRepository para obtener operaciones CRUD básicas.
 * Implementa EquipoRepositoryCustom para búsquedas complejas.
 *
 * Métodos heredados automáticamente de MongoRepository:
 * - save(), saveAll(), findById(), findAll(), count(), deleteById(), delete(), deleteAll(), etc.
 */
@Repository
public interface EquipoRepository extends MongoRepository<Equipo, String>, EquipoRepositoryCustom {

    /**
     * Busca equipos por nombre de forma case-insensitive.
     *
     * @param nombre Nombre a buscar
     * @param pageable Configuración de paginación
     * @return Página de equipos encontrados
     */
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Page<Equipo> findByNombreIgnoreCase(String nombre, Pageable pageable);

    /**
     * Busca equipos por nombre de forma case-insensitive.
     *
     * @param nombre Nombre a buscar
     * @return Página de equipos encontrados
     */
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Optional<Equipo> findByNombreIgnoreCase(String nombre);

    /**
     * Busca equipos por ID de País asociado.
     *
     * @param paisId ID del país
     * @param pageable Configuración de paginación
     * @return Página de equipos del país
     */
    @Query("{ 'pais.id': ?0 }")
    Page<Equipo> findByPaisId(String paisId, Pageable pageable);

    /**
     * Busca equipos por ID de Competición asociada.
     *
     * @param competicionId ID de la competición
     * @param pageable Configuración de paginación
     * @return Página de equipos de la competición
     */
    @Query("{ 'competicion.id': ?0 }")
    Page<Equipo> findByCompeticionId(String competicionId, Pageable pageable);

    /**
     * Verifica si existe un equipo con un país específico.
     *
     * @param paisId ID del país
     * @return true si existe, false en caso contrario
     */
    @Query("{ 'pais.id': ?0 }")
    boolean existsByPaisId(String paisId);

    /**
     * Verifica si existe un equipo con una competición específica.
     *
     * @param competicionId ID de la competición
     * @return true si existe, false en caso contrario
     */
    @Query("{ 'competicion.id': ?0 }")
    boolean existsByCompeticionId(String competicionId);

    /**
     * Busca equipos por nombre exacto.
     *
     * @param nombre Nombre exacto a buscar
     * @param pageable Configuración de paginación
     * @return Página de equipos encontrados
     */
    @Query("{ 'nombre': ?0 } }")
    Page<Equipo> findByNombre(String nombre, Pageable pageable);
}


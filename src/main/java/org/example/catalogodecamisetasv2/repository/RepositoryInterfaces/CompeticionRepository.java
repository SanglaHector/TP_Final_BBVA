package org.example.catalogodecamisetasv2.repository.RepositoryInterfaces;

import org.example.catalogodecamisetasv2.model.Competicion;
import org.example.catalogodecamisetasv2.repository.RepositoryCustom.CompeticionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Competicion usando MongoDB.
 * Extiende MongoRepository para obtener operaciones CRUD básicas.
 * Implementa CompeticionRepositoryCustom para búsquedas complejas.
 *
 * Métodos heredados automáticamente de MongoRepository:
 * - save(), saveAll(), findById(), findAll(), count(), deleteById(), delete(), deleteAll(), etc.
 */
@Repository
public interface CompeticionRepository extends MongoRepository<Competicion, String>, CompeticionRepositoryCustom {

    /**
     * Busca competiciones por nombre de forma case-insensitive.
     *
     * @param nombre Nombre a buscar
     * @param pageable Configuración de paginación
     * @return Página de competiciones encontradas
     */
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Page<Competicion> findByNombreIgnoreCase(String nombre, Pageable pageable);

    /**
     * Verifica si existe una competición con un nombre específico (case-insensitive).
     * @Param nombre Nombre de la competición
     * @return true si existe, false en caso contrario
     */
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Optional<Competicion> findByNombreIgnoreCase(String nombre);
    /**
     * Busca competiciones por ID de País asociado.
     *
     * @param paisId ID del país
     * @param pageable Configuración de paginación
     * @return Página de competiciones del país
     */
    @Query("{ 'pais.id': ?0 }")
    Page<Competicion> findByPaisId(String paisId, Pageable pageable);

    /**
     * Verifica si existe una competición con un país específico.
     *
     * @param paisId ID del país
     * @return true si existe, false en caso contrario
     */
    @Query("{ 'pais.id': ?0 }")
    boolean existsByPaisId(String paisId);


}


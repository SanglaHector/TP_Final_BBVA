package org.example.catalogodecamisetasv2.repository.RepositoryInterfaces;

import org.example.catalogodecamisetasv2.model.Pais;
import org.example.catalogodecamisetasv2.repository.RepositoryCustom.PaisRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Pais usando MongoDB.
 * Extiende MongoRepository para obtener operaciones CRUD básicas.
 * Implementa PaisRepositoryCustom para búsquedas complejas.
 *
 * Métodos heredados automáticamente de MongoRepository:
 * - save(), saveAll(), findById(), findAll(), count(), deleteById(), delete(), deleteAll(), etc.
 */
@Repository
public interface PaisRepository extends MongoRepository<Pais, String>, PaisRepositoryCustom {

    /**
     * Busca países por nombre de forma case-insensitive.
     *
     * @param nombre Nombre a buscar
     * @param pageable Configuración de paginación
     * @return Página de países encontrados
     */
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Page<Pais> findByNombreIgnoreCase(String nombre, Pageable pageable);

    /**
     * Busca un país por nombre exacto de forma case-insensitive.
     *
     * @param nombre Nombre del país
     * @return País encontrado o vacío
     */
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Page<Pais> findByNombreExacto(String nombre, Pageable pageable);

    /**
     * Busca un país por nombre exacto de forma case-insensitive y devuelve un Optional.
     * @param nombre
     * @return Optional con el país encontrado o vacío
     */
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Optional<Pais> findByNombreIgnoreCase(String nombre);

}


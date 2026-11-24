package org.example.catalogodecamisetasv2.repository.RepositoryInterfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica para repositorios basados en JSON. -Se adapta a MongoDB u otros sistemas de persistencia si se implementa adecuadamente.
 * Proporciona métodos CRUD comunes reutilizables para cualquier modelo de entidad.
 *
 * @param <T> Tipo de entidad que maneja el repositorio
 */
public interface GenericRepository<T> {

    /**
     * Obtiene todas las entidades.
     *
     * @return Lista de todas las entidades
     */
    List<T> findAll();

    /**
     * Obtiene todas las entidades con paginación.
     *
     * @param pageable Configuración de paginación
     * @return Página de entidades
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Obtiene una entidad por su ID.
     *
     * @param id ID de la entidad
     * @return Optional con la entidad si existe, sino vacío
     */
    Optional<T> findById(Long id);

    /**
     * Guarda una entidad. Si no tiene ID, se genera uno automáticamente.
     *
     * @param entity Entidad a guardar
     * @return Entidad guardada con ID asignado
     */
    T save(T entity);

    /**
     * Elimina una entidad por su ID.
     *
     * @param id ID de la entidad a eliminar
     */
    void deleteById(Long id);

    /**
     * Cuenta el total de entidades.
     *
     * @return Número de entidades
     */
    long count();

    /**
     * Verifica si existe una entidad con el ID especificado.
     *
     * @param id ID a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existsById(Long id);
}


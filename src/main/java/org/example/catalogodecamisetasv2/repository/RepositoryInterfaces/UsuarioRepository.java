package org.example.catalogodecamisetasv2.repository.RepositoryInterfaces;

import org.example.catalogodecamisetasv2.model.Usuario;
import org.example.catalogodecamisetasv2.repository.RepositoryCustom.UsuarioRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuario usando MongoDB.
 * Extiende MongoRepository para obtener operaciones CRUD básicas.
 * Implementa UsuarioRepositoryCustom para búsquedas complejas.
 *
 * Métodos heredados automáticamente de MongoRepository:
 * - save(), saveAll(), findById(), findAll(), count(), deleteById(), delete(), deleteAll(), etc.
 */
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>, UsuarioRepositoryCustom {

    /**
     * Busca un usuario por nombre de forma case-insensitive.
     *
     * @param nombre Nombre a buscar
     * @return Optional con el usuario encontrado
     */
    @Query("{ 'nombre': { $regex: ?0, $options: 'i' } }")
    Optional<Usuario> findByNombreIgnoreCase(String nombre);
}


package org.example.catalogodecamisetasv2.repository.RepositoryImpl;

import org.example.catalogodecamisetasv2.repository.RepositoryCustom.UsuarioRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * Implementación de métodos custom del repositorio de Usuario.
 * Utiliza MongoTemplate para realizar queries complejas si es necesario.
 */
@Component
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {
    // Implementación de métodos custom si es necesario
    private final MongoTemplate mongoTemplate;

    public UsuarioRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}


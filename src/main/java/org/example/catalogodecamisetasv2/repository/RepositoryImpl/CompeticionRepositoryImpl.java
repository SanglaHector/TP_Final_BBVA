package org.example.catalogodecamisetasv2.repository.RepositoryImpl;

import org.example.catalogodecamisetasv2.dto.CompeticionRequestDTO;

import org.example.catalogodecamisetasv2.model.Competicion;
import org.example.catalogodecamisetasv2.repository.RepositoryCustom.CompeticionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * Implementación de métodos custom del repositorio de Competicion.
 * Utiliza MongoTemplate para realizar queries complejas si es necesario.
 */
@Component
public class CompeticionRepositoryImpl implements CompeticionRepositoryCustom {
    // Implementación de métodos custom si es necesario
    private final MongoTemplate mongoTemplate;
    public CompeticionRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public Page<Competicion> buscarConFiltros(CompeticionRequestDTO filtros, Pageable pageable) {
        // Crear un objeto Criteria vacío que acumulará las condiciones
        Criteria criteria = new Criteria();

        // Filtro por nombre de la competición (case-insensitive)
        if (filtros.getNombre() != null && !filtros.getNombre().isBlank()) {
            criteria = criteria.and("nombre")
                    .regex("^" + filtros.getNombre() + "$", "i");
        }
        // Filtro por Id del país de la competición
       if (filtros.getPaisId() != null) {
            criteria = criteria.and("pais").is(filtros.getPaisId());
        }
        // Crear la query con los criterios y la paginación
        Query query = new Query(criteria).with(pageable);

        // Contar el total de documentos que coinciden con los criterios
        long count = mongoTemplate.count(query, Competicion.class);
        // Obtener la página de resultados
        List<Competicion> results = mongoTemplate.find(query, Competicion.class);

        // Retornar la página con los resultados, configuración de paginación y total
        return new PageImpl<>(results, pageable, count);
    }
}


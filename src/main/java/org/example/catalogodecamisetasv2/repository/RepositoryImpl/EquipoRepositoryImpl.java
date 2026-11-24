package org.example.catalogodecamisetasv2.repository.RepositoryImpl;

import org.example.catalogodecamisetasv2.dto.EquipoRequestDTO;
import org.example.catalogodecamisetasv2.model.Equipo;
import org.example.catalogodecamisetasv2.repository.RepositoryCustom.EquipoRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementación de métodos custom del repositorio de Equipo.
 * Utiliza MongoTemplate para realizar queries complejas si es necesario.
 */
@Component
public class EquipoRepositoryImpl implements EquipoRepositoryCustom {
    // Implementación de métodos custom si es necesario
    private final MongoTemplate mongoTemplate;
    public EquipoRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public Page<Equipo> buscarConFiltros(EquipoRequestDTO filtros, Pageable pageable) {
        // Crear un objeto Criteria vacío que acumulará las condiciones
        Criteria criteria = new Criteria();

        // Filtro por nombre del equipo (case-insensitive)
        if (filtros.getNombre() != null && !filtros.getNombre().isBlank()) {
            criteria = criteria.and("nombre")
                    .regex("^" + filtros.getNombre() + "$", "i");
        }
        // Filtro por Id del país del equipo
        if (filtros.getPaisId() != null) {
            criteria = criteria.and("pais").is(filtros.getPaisId());
        }
        //Filtro por Id de la competición del equipo
        if (filtros.getCompeticionId() != null) {
            criteria = criteria.and("competicion").is(filtros.getCompeticionId());
        }
        //Filtro por tipo de equipo (enum)
        if (filtros.getTipo() != null) {
            criteria = criteria.and("tipo").is(filtros.getTipo());
        }

        // Crear la query con los criterios y la paginación
        Query query = new Query(criteria).with(pageable);

        // Contar el total de documentos que coinciden con los criterios
        long count = mongoTemplate.count(query, Equipo.class);
        // Obtener la página de resultados
        List<Equipo> results = mongoTemplate.find(query, Equipo.class);

        // Retornar la página con los resultados, configuración de paginación y total
        return new PageImpl<>(results, pageable, count);
    }
}


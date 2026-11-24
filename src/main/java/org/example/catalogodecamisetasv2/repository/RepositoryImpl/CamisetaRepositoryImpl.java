package org.example.catalogodecamisetasv2.repository.RepositoryImpl;

import org.example.catalogodecamisetasv2.dto.CamisetaRequestDTO;
import org.example.catalogodecamisetasv2.model.Camiseta;
import org.example.catalogodecamisetasv2.repository.RepositoryCustom.CamisetaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Implementación de métodos custom del repositorio de Camiseta.
 * Utiliza MongoTemplate para realizar queries complejas y búsquedas dinámicas
 * con filtros opcionales en MongoDB.
 */
@Component
public class CamisetaRepositoryImpl implements CamisetaRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public CamisetaRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Busca camisetas con filtros dinámicos basados en CamisetaRequestDTO.
     * Solo aplica los criterios que no sean nulos.
     *
     * @param filtros DTO con los criterios de búsqueda (equipoId, temporada, precio, talle, marca, esRetro)
     * @param pageable Configuración de paginación
     * @return Página de Camisetas que coinciden con los filtros aplicados
     */
    @Override
    public Page<Camiseta> buscarConFiltros(CamisetaRequestDTO filtros, Pageable pageable) {
        // Crear un objeto Criteria vacío que acumulará las condiciones
        Criteria criteria = new Criteria();

        // Filtro por ID de Equipo
        if (filtros.getEquipoId() != null) {
            criteria = criteria.and("equipo").is(filtros.getEquipoId());
        }

        // Filtro por Temporada (case-insensitive)
        if (filtros.getTemporada() != null && !filtros.getTemporada().isBlank()) {
            criteria = criteria.and("temporada")
                    .regex("^" + filtros.getTemporada() + "$", "i");
        }

        // Filtro por Precio (exacto)
        if (filtros.getPrecio() != null) {
            criteria = criteria.and("precio").is(filtros.getPrecio());
        }

        // Filtro por Talle (enum)
        if (filtros.getTalle() != null) {
            criteria = criteria.and("talle").is(filtros.getTalle());
        }

        // Filtro por Marca (case-insensitive)
        if (filtros.getMarca() != null && !filtros.getMarca().isBlank()) {
            criteria = criteria.and("marca")
                    .regex("^" + filtros.getMarca() + "$", "i");
        }

        // Filtro por Es Retro (booleano)
        if (filtros.getEsRetro() != null) {
            criteria = criteria.and("esRetro").is(filtros.getEsRetro());
        }

        // Crear la query con los criterios y la paginación
        Query query = new Query(criteria).with(pageable);

        // Contar el total de documentos que coinciden con los criterios
        long count = mongoTemplate.count(query, Camiseta.class);
        // Obtener la página de resultados
        List<Camiseta> results = mongoTemplate.find(query, Camiseta.class);

        // Retornar la página con los resultados, configuración de paginación y total
        return new PageImpl<>(results, pageable, count);
    }

    /**
     * Busca camisetas por ID de Competición usando agregación con lookup.
     * Realiza un join con la colección de equipos para encontrar camisetas
     * cuyo equipo tiene la competición especificada.
     *
     * @param competicionId ID de la competición
     * @param pageable Configuración de paginación
     * @return Página de camisetas de la competición
     */
    public Page<Camiseta> findByEquipoCompeticionId(String competicionId, Pageable pageable) {
        // Crear una agregación con lookup a la colección de equipos
        MatchOperation match = Aggregation.match(
            Criteria.where("equipo").exists(true)
        );
        
        LookupOperation lookup = Aggregation.lookup("equipos", "equipo", "_id", "equipoInfo");
        UnwindOperation unwind = Aggregation.unwind("equipoInfo");
        MatchOperation matchCompeticion = Aggregation.match(
            Criteria.where("equipoInfo.competicion").is(competicionId)
        );
        SkipOperation skip = Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize());
        LimitOperation limit = Aggregation.limit(pageable.getPageSize());
        
        Aggregation aggregation = Aggregation.newAggregation(
            match, lookup, unwind, matchCompeticion, skip, limit
        );
        
        AggregationResults<Camiseta> results = mongoTemplate.aggregate(
            aggregation, "camisetas", Camiseta.class
        );
        
        // Contar el total usando una agregación sin paginación
        Aggregation countAggregation = Aggregation.newAggregation(
            match, lookup, unwind, matchCompeticion,
            Aggregation.count().as("total")
        );
        
        AggregationResults<Map> countResults = mongoTemplate.aggregate(
            countAggregation, "camisetas", Map.class
        );
        
        long count = countResults.getMappedResults().isEmpty() ? 0 : 
            ((Number) countResults.getMappedResults().get(0).get("total")).longValue();
        
        return new PageImpl<>(results.getMappedResults(), pageable, count);
    }

    /**
     * Busca camisetas por ID de País usando agregación con lookup.
     * Realiza un join con la colección de equipos para encontrar camisetas
     * cuyo equipo tiene el país especificado.
     *
     * @param paisId ID del país
     * @param pageable Configuración de paginación
     * @return Página de camisetas del país
     */
    public Page<Camiseta> findByEquipoPaisId(String paisId, Pageable pageable) {
        // Crear una agregación con lookup a la colección de equipos
        MatchOperation match = Aggregation.match(
            Criteria.where("equipo").exists(true)
        );
        
        LookupOperation lookup = Aggregation.lookup("equipos", "equipo", "_id", "equipoInfo");
        UnwindOperation unwind = Aggregation.unwind("equipoInfo");
        MatchOperation matchPais = Aggregation.match(
            Criteria.where("equipoInfo.pais").is(paisId)
        );
        SkipOperation skip = Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize());
        LimitOperation limit = Aggregation.limit(pageable.getPageSize());
        
        Aggregation aggregation = Aggregation.newAggregation(
            match, lookup, unwind, matchPais, skip, limit
        );
        
        AggregationResults<Camiseta> results = mongoTemplate.aggregate(
            aggregation, "camisetas", Camiseta.class
        );
        
        // Contar el total usando una agregación sin paginación
        Aggregation countAggregation = Aggregation.newAggregation(
            match, lookup, unwind, matchPais,
            Aggregation.count().as("total")
        );
        
        AggregationResults<Map> countResults = mongoTemplate.aggregate(
            countAggregation, "camisetas", Map.class
        );
        
        long count = countResults.getMappedResults().isEmpty() ? 0 : 
            ((Number) countResults.getMappedResults().get(0).get("total")).longValue();
        
        return new PageImpl<>(results.getMappedResults(), pageable, count);
    }
}

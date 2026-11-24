package org.example.catalogodecamisetasv2.repository.RepositoryCustom;

import org.example.catalogodecamisetasv2.dto.CamisetaRequestDTO;
import org.example.catalogodecamisetasv2.model.Camiseta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz personalizada para métodos custom del repositorio de Camiseta.
 * Define contratos para búsquedas complejas que no pueden ser generadas automáticamente
 * por Spring Data MongoDB.
 */
public interface CamisetaRepositoryCustom {

    /**
     * Busca camisetas según múltiples criterios opcionales con filtros dinámicos.
     * Solo aplica los criterios que no sean nulos.
     *
     * @param filtros DTO con los criterios de búsqueda
     * @param pageable Configuración de paginación
     * @return Página de Camisetas que coinciden con los filtros
     */
    Page<Camiseta> buscarConFiltros(CamisetaRequestDTO filtros, Pageable pageable);

    /**
     * Busca camisetas por ID de Competición.
     * Utiliza agregación para hacer un join con la colección de equipos.
     *
     * @param competicionId ID de la competición
     * @param pageable Configuración de paginación
     * @return Página de camisetas de esa competición
     */
    Page<Camiseta> findByEquipoCompeticionId(String competicionId, Pageable pageable);

    /**
     * Busca camisetas por ID de País.
     * Utiliza agregación para hacer un join con la colección de equipos.
     *
     * @param paisId ID del país
     * @param pageable Configuración de paginación
     * @return Página de camisetas de ese país
     */
    Page<Camiseta> findByEquipoPaisId(String paisId, Pageable pageable);
}

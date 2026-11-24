package org.example.catalogodecamisetasv2.repository.RepositoryCustom;

import org.example.catalogodecamisetasv2.dto.CompeticionRequestDTO;
import org.example.catalogodecamisetasv2.model.Competicion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz personalizada para métodos custom del repositorio de Competicion.
 * Define contratos para búsquedas complejas en MongoDB.
 */
public interface CompeticionRepositoryCustom {
    // Aquí se pueden agregar métodos custom para búsquedas complejas si es necesario
    /**
     * Busca competicion según múltiples criterios opcionales con filtros dinámicos.
     * Solo aplica los criterios que no sean nulos.
     *
     * @param filtros DTO con los criterios de búsqueda
     * @param pageable Configuración de paginación
     * @return Página de Camisetas que coinciden con los filtros
     */
    Page<Competicion> buscarConFiltros(CompeticionRequestDTO filtros, Pageable pageable);
}


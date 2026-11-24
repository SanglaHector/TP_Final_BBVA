package org.example.catalogodecamisetasv2.repository.RepositoryCustom;

import org.example.catalogodecamisetasv2.dto.EquipoRequestDTO;
import org.example.catalogodecamisetasv2.model.Equipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz personalizada para métodos custom del repositorio de Equipo.
 * Define contratos para búsquedas complejas en MongoDB.
 */
public interface EquipoRepositoryCustom {
    // Aquí se pueden agregar métodos custom para búsquedas complejas si es necesario
    /**
     * Busca equipo según múltiples criterios opcionales con filtros dinámicos.
     * Solo aplica los criterios que no sean nulos.
     * @param filtros DTO con los criterios de búsqueda
     * @param pageable Configuración de paginación
     */
    Page<Equipo> buscarConFiltros(EquipoRequestDTO filtros, Pageable pageable);
}


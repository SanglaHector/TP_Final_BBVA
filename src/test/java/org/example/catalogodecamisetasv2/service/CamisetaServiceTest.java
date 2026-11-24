package org.example.catalogodecamisetasv2.service;

import org.example.catalogodecamisetasv2.dto.CamisetaRequestDTO;
import org.example.catalogodecamisetasv2.dto.CamisetaResponseDTO;
import org.example.catalogodecamisetasv2.exception.CamisetaNotFoundException;
import org.example.catalogodecamisetasv2.exception.DatosInvalidosException;
import org.example.catalogodecamisetasv2.mapper.CamisetaMapper;
import org.example.catalogodecamisetasv2.model.Camiseta;
import org.example.catalogodecamisetasv2.model.Equipo;
import org.example.catalogodecamisetasv2.model.Talle;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.CamisetaRepository;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.EquipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para CamisetaService
 *
 * Estructura: DADO (Given) - CUANDO (When) - ENTONCES (Then)
 * Ejemplo:
 *   DADO: Un servicio con un repositorio simulado
 *   CUANDO: Se llama a guardar una camiseta
 *   ENTONCES: Debe guardar y devolver la camiseta creada
 */
@DisplayName("Tests del Servicio de Camisetas")
class CamisetaServiceTest {

    /**
     * @Mock: Crea un objeto simulado (fake) del repositorio
     * Este objeto fingirá ser un repositorio pero no accede a la BD real
     */
    @Mock
    private CamisetaRepository camisetaRepository;

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private CamisetaMapper camisetaMapper;

    /**
     * @InjectMocks: Inyecta los mocks en CamisetaService
     * CamisetaService usará los repositorios simulados
     */
    @InjectMocks
    private CamisetaService camisetaService;

    /**
     * Este método se ejecuta ANTES de cada test
     * Inicializa todos los @Mock y @InjectMocks
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ==================== TESTS DE CREAR CAMISETA ====================

    @Test
    @DisplayName("Debe crear una camiseta exitosamente")
    void testGuardarCamisetaExitosamente() {
        // DADO: Un DTO de camiseta y un equipo que existe
        CamisetaRequestDTO requestDTO = CamisetaRequestDTO.builder()
                .equipoId("1")
                .temporada("2023-2024")
                .precio(89.99)
                .talle(Talle.L)
                .stock(50)
                .marca("Nike")
                .esRetro(false)
                .build();

        Equipo equipo = new Equipo();
        equipo.setId("1");
        equipo.setNombre("Real Madrid");

        Camiseta camisetaGuardada = new Camiseta();
        camisetaGuardada.setId("1");
        camisetaGuardada.setEquipo(equipo);
        camisetaGuardada.setTemporada("2023-2024");

        // CUANDO: Mock el comportamiento del repositorio
        when(equipoRepository.findById("1")).thenReturn(Optional.of(equipo));
        when(camisetaRepository.save(any(Camiseta.class))).thenReturn(camisetaGuardada);
        when(camisetaMapper.toCamisetaResponseDTO(camisetaGuardada))
                .thenReturn(new CamisetaResponseDTO());

        // ENTONCES: Llamamos al servicio
        CamisetaResponseDTO resultado = camisetaService.guardar(requestDTO);

        // Y verificamos
        assertNotNull(resultado);
        verify(camisetaRepository, times(1)).save(any(Camiseta.class));
        verify(equipoRepository, times(1)).findById("1");
    }

    @Test
    @DisplayName("Debe lanzar excepción si el equipo no existe")
    void testGuardarCamisetaSinEquipo() {
        // DADO: Un DTO con ID de equipo que NO existe
        CamisetaRequestDTO requestDTO = CamisetaRequestDTO.builder()
                .equipoId("999")
                .temporada("2023-2024")
                .precio(89.99)
                .talle(Talle.L)
                .stock(50)
                .marca("Nike")
                .build();

        // CUANDO: El repositorio de equipos devuelve vacío
        when(equipoRepository.findById("999")).thenReturn(Optional.empty());

        // ENTONCES: Debe lanzar excepción
        assertThrows(DatosInvalidosException.class, () -> {
            camisetaService.guardar(requestDTO);
        });
    }

    // ==================== TESTS DE OBTENER CAMISETA ====================

    @Test
    @DisplayName("Debe obtener una camiseta por ID")
    void testObtenerCamisetaPorIdExitosamente() {
        // DADO: Una camiseta que existe en la BD
        String camisetaId = "1";
        Camiseta camiseta = new Camiseta();
        camiseta.setId(camisetaId);
        camiseta.setTemporada("2023-2024");

        // CUANDO: El repositorio devuelve la camiseta
        when(camisetaRepository.findById(camisetaId)).thenReturn(Optional.of(camiseta));
        when(camisetaMapper.toCamisetaResponseDTO(camiseta))
                .thenReturn(new CamisetaResponseDTO());

        // ENTONCES: Debe devolver un Optional con la camiseta
        Optional<CamisetaResponseDTO> resultado = camisetaService.obtenerCamisetaPorId(camisetaId);

        assertTrue(resultado.isPresent());
        verify(camisetaRepository, times(1)).findById(camisetaId);
    }

    @Test
    @DisplayName("Debe devolver vacío si la camiseta no existe")
    void testObtenerCamisetaPorIdNoExiste() {
        // DADO: Un ID de camiseta que no existe
        when(camisetaRepository.findById("999")).thenReturn(Optional.empty());

        // ENTONCES: Debe devolver Optional.empty()
        Optional<CamisetaResponseDTO> resultado = camisetaService.obtenerCamisetaPorId("999");

        assertFalse(resultado.isPresent());
    }

    // ==================== TESTS DE LISTAR CAMISETAS ====================

    @Test
    @DisplayName("Debe listar todas las camisetas con paginación")
    void testObtenerCamisetasConPaginacion() {
        // DADO: Una página con camisetas
        Pageable pageable = PageRequest.of(0, 5);
        List<Camiseta> camisetas = Arrays.asList(
                new Camiseta(),
                new Camiseta(),
                new Camiseta()
        );
        Page<Camiseta> pagina = new PageImpl<>(camisetas, pageable, 3);

        // CUANDO: El repositorio devuelve la página
        when(camisetaRepository.findAll(pageable)).thenReturn(pagina);
        when(camisetaMapper.toCamisetaResponseDTO(any(Camiseta.class)))
                .thenReturn(new CamisetaResponseDTO());

        // ENTONCES: Debe devolver una página con resultados
        Page<CamisetaResponseDTO> resultado = camisetaService.obtenerCamisetas(pageable);

        assertEquals(3, resultado.getContent().size());
        verify(camisetaRepository, times(1)).findAll(pageable);
    }

    // ==================== TESTS DE ACTUALIZAR STOCK ====================

    @Test
    @DisplayName("Debe actualizar el stock de una camiseta")
    void testActualizarStockExitosamente() {
        // DADO: Una camiseta que existe y un nuevo stock
        String camisetaId = "1";
        int nuevoStock = 100;

        Camiseta camiseta = new Camiseta();
        camiseta.setId(camisetaId);
        camiseta.setStock(50);

        Camiseta camisetaActualizada = new Camiseta();
        camisetaActualizada.setId(camisetaId);
        camisetaActualizada.setStock(nuevoStock);

        // CUANDO: Llamamos a actualizar stock
        when(camisetaRepository.findById(camisetaId)).thenReturn(Optional.of(camiseta));
        when(camisetaRepository.save(any(Camiseta.class))).thenReturn(camisetaActualizada);
        when(camisetaMapper.toCamisetaResponseDTO(camisetaActualizada))
                .thenReturn(new CamisetaResponseDTO());

        // ENTONCES: Debe actualizar correctamente
        CamisetaResponseDTO resultado = camisetaService.actualizarStock(camisetaId, nuevoStock);

        assertNotNull(resultado);
        verify(camisetaRepository, times(1)).save(any(Camiseta.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el stock es negativo")
    void testActualizarStockNegativo() {
        // DADO: Una camiseta y stock negativo
        String camisetaId =" 1";
        Camiseta camiseta = new Camiseta();
        camiseta.setId(camisetaId);

        // CUANDO: El repositorio encuentra la camiseta
        when(camisetaRepository.findById(camisetaId)).thenReturn(Optional.of(camiseta));

        // ENTONCES: Debe lanzar excepción por stock negativo
        assertThrows(DatosInvalidosException.class, () -> {
            camisetaService.actualizarStock(camisetaId, -10);
        });
    }

    // ==================== TESTS DE ELIMINAR CAMISETA ====================

    @Test
    @DisplayName("Debe eliminar una camiseta exitosamente")
    void testEliminarCamisetaExitosamente() {
        // DADO: Una camiseta que existe
        String camisetaId =" 1";

        // CUANDO: El repositorio confirma que existe
        when(camisetaRepository.existsById(camisetaId)).thenReturn(true);

        // ENTONCES: Llamamos a eliminar
        assertDoesNotThrow(() -> camisetaService.eliminar(camisetaId));
        verify(camisetaRepository, times(1)).deleteById(camisetaId);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar camiseta inexistente")
    void testEliminarCamisetaNoExiste() {
        // DADO: Una camiseta que no existe
        when(camisetaRepository.existsById("999")).thenReturn(false);

        // ENTONCES: Debe lanzar excepción
        assertThrows(CamisetaNotFoundException.class, () -> {
            camisetaService.eliminar("999");
        });
    }
}


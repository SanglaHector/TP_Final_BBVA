package org.example.catalogodecamisetasv2.controller;

import org.example.catalogodecamisetasv2.dto.CamisetaRequestDTO;
import org.example.catalogodecamisetasv2.dto.CamisetaResponseDTO;
import org.example.catalogodecamisetasv2.model.Talle;
import org.example.catalogodecamisetasv2.service.CamisetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TEST DE LA CAPA WEB usando MockMvcBuilders.
 *
 * Este enfoque es una alternativa a @WebMvcTest que funciona en todas las versiones de Spring Boot.
 *
 * @ExtendWith(MockitoExtension.class)
 *   - Habilita las anotaciones @Mock de Mockito en la clase.
 *   - Es más ligero que levantar el contexto de Spring.
 *
 * MockMvcBuilders.standaloneSetup()
 *   - Crea un MockMvc sin levantar el contexto de Spring.
 *   - Solo prueba el controlador de forma aislada.
 *   - Es muy rápido porque no inicializa MongoDB ni otras dependencias.
 *
 * @Mock
 *   - Crea un mock de Mockito (similar a @MockitoBean en Spring).
 */
@ExtendWith(MockitoExtension.class)
public class CamisetaControllerWebMvcTest {

    private MockMvc mockMvc;

    @Mock
    private CamisetaService camisetaService;

    @BeforeEach
    void setUp() {
        // Inicializar MockMvc con el controlador y sus dependencias mockeadas
        mockMvc = MockMvcBuilders.standaloneSetup(new CamisetaController(camisetaService))
                .build();
    }

    @Test
    void debeCrearCamiseta() throws Exception {
        // Preparar datos de respuesta esperada
        CamisetaResponseDTO camisetaGuardada = CamisetaResponseDTO.builder()
                .id("camiseta123")
                .marca("Nike")
                .talle(Talle.L)
                .precio(99.99)
                .stock(50)
                .temporada("2024-2025")
                .esRetro(false)
                .build();

        // Configurar el mock: cuando se llame a guardar() con cualquier CamisetaRequestDTO,
        // retornar camisetaGuardada
        when(camisetaService.guardar(any(CamisetaRequestDTO.class)))
                .thenReturn(camisetaGuardada);

        // Preparar el JSON de entrada - IMPORTANTE: formato de temporada es YYYY-YYYY
        String json = "{\"equipoId\":\"eq123\",\"temporada\":\"2024-2025\",\"precio\":99.99," +
                "\"talle\":\"L\",\"stock\":50,\"marca\":\"Nike\",\"esRetro\":false}";

        // perform(...)      -> ejecuta la petición simulada.
        // post("/api/camisetas") -> método HTTP POST y ruta.
        // contentType(...)  -> avisa que el cuerpo es JSON (header Content-Type).
        // content(json)     -> el cuerpo de la petición.
        // andExpect(...)    -> verificaciones sobre la respuesta.
        mockMvc.perform(post("/api/camisetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())                           // HTTP 201
                .andExpect(jsonPath("$.id").value("camiseta123"))          // $ = raíz del JSON
                .andExpect(jsonPath("$.marca").value("Nike"))
                .andExpect(jsonPath("$.precio").value(99.99))
                .andExpect(jsonPath("$.stock").value(50));
    }

    @Test
    void debeResponder404SiLaCamisetaNoExiste() throws Exception {
        // Configurar el mock: cuando se busque por ID "xyz", lanzar excepción
        when(camisetaService.obtenerCamisetaPorId("xyz"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/camisetas/{id}", "xyz"))
                .andExpect(status().isNotFound());                         // HTTP 404
    }

    @Test
    void debeObtenerCamisetaPorId() throws Exception {
        // Preparar datos de respuesta esperada
        CamisetaResponseDTO camisetaEncontrada = CamisetaResponseDTO.builder()
                .id("camiseta123")
                .marca("Adidas")
                .talle(Talle.M)
                .precio(79.99)
                .stock(100)
                .temporada("2023-2024")
                .esRetro(true)
                .build();

        // Configurar el mock: cuando se busque por ID "camiseta123", retornar la camiseta
        when(camisetaService.obtenerCamisetaPorId("camiseta123"))
                .thenReturn(Optional.of(camisetaEncontrada));

        mockMvc.perform(get("/api/camisetas/{id}", "camiseta123"))
                .andExpect(status().isOk())                               // HTTP 200
                .andExpect(jsonPath("$.id").value("camiseta123"))
                .andExpect(jsonPath("$.marca").value("Adidas"))
                .andExpect(jsonPath("$.talle").value("M"))
                .andExpect(jsonPath("$.precio").value(79.99))
                .andExpect(jsonPath("$.esRetro").value(true));
    }
}

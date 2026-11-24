package org.example.catalogodecamisetasv2.repository;

import org.example.catalogodecamisetasv2.model.Pais;
import org.example.catalogodecamisetasv2.repository.RepositoryImpl.PaisRepositoryImpl;
import org.example.catalogodecamisetasv2.repository.RepositoryInterfaces.PaisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
//Significa que si docker no esta corriendo, los tests se marcan como "omitidos" en lugar de fallar.
// No tengo Docker para probar estas pruebas.
@Testcontainers(disabledWithoutDocker = true)
public class PaisRepositoryImplTest {

    @Container
    static MongoDBContainer mongoDB = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.mongodb.uri", mongoDB::getReplicaSetUrl);
    }

    @Autowired
    PaisRepositoryImpl paisRepository;

    @Autowired
    PaisRepository paisRepositoryInterface;

    @BeforeEach
    void setUp() {
        assertNotNull(paisRepository);
    }

    @Test
    void debeInyectarElRepositorio() {
        assertNotNull(paisRepository);
    }

    @Test
    void debeGuardarYBuscarUnPais() {
        // Crear un país
        Pais pais = new Pais();
        pais.setNombre("Argentina");

        // Guardar el país
        Pais paisGuardado = paisRepositoryInterface.save(pais);
        assertNotNull(paisGuardado);
        assertNotNull(paisGuardado.getId());

        // Buscar el país por ID
        Optional<Pais> paisEncontrado = paisRepositoryInterface.findById(paisGuardado.getId());
        assertTrue(paisEncontrado.isPresent());
        assertEquals("Argentina", paisEncontrado.get().getNombre());
    }

}


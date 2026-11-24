package org.example.catalogodecamisetasv2.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuración de MongoDB y Jackson para la deserialización de documentos.
 */
@Configuration
@EnableMongoRepositories(basePackages = "org.example.catalogodecamisetasv2.repository.RepositoryInterfaces")
public class MongoDBConfiguration {

    /**
     * Configura ObjectMapper para permitir deserialización más flexible,
     * especialmente para enums y campos desconocidos.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // No permitir deserializar valores enum por nombre (case-insensitive)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        // No permitir deserializar valores enum desconocidos usando un valor por defecto
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, false);
        return mapper;
    }
}


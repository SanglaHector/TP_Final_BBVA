package org.example.catalogodecamisetasv2.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para representar respuestas de error en la API.
 * Se utiliza para enviar mensajes de error consistentes en el body de la respuesta HTTP.
 */
@Getter
@Setter
public class ErrorResponseDTO {
    private int statusCode;
    private String mensaje;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(int statusCode, String mensaje) {
        this.statusCode = statusCode;
        this.mensaje = mensaje;
        this.timestamp = LocalDateTime.now();
    }
}


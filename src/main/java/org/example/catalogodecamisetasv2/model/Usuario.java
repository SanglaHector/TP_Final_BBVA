package org.example.catalogodecamisetasv2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modelo de Usuario.
 * Representa un usuario en el sistema con credenciales y rol.
 * Se almacena en la colección 'usuarios' de MongoDB.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String clave;
    private Rol rol;
}


package org.example.catalogodecamisetasv2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "equipos")
public class Equipo {
    @Id
    private String id;
    private String nombre;
    @DocumentReference
    private Pais pais;
    @DocumentReference
    private Competicion competicion;
    private TipoEquipo tipo;
}
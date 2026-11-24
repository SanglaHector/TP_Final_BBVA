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
@Document(collection = "camisetas")
public class Camiseta {
    @Id
    private String id;
    @DocumentReference
    private Equipo equipo;
    private String temporada;
    private Double precio;
    private Talle talle;
    private Integer stock;
    private String marca;
    private Boolean esRetro;
}
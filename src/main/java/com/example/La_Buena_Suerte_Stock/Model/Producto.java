package com.example.La_Buena_Suerte_Stock.Model;

import com.example.La_Buena_Suerte_Stock.Enums.Ecategoria;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true)
    private String codigo;

    private String nombre;

    private Integer stockActual;

    @Column(name = "precio")
    private double precio;

    private Integer stockMinimo;

    @Enumerated(EnumType.STRING)
    private Ecategoria categoria;

    private Boolean activo = true;
}
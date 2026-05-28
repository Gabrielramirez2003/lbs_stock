package com.example.La_Buena_Suerte_Stock.Model;

import com.example.La_Buena_Suerte_Stock.Enums.Ecategoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="producto")
public class Producto {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="codigo")
    private String codigo;

    private String nombre;
    private Integer stockActual;
    @Column(name="precio")
    private double precio;

    private int stockMinimo;
    private Ecategoria categoria;
}

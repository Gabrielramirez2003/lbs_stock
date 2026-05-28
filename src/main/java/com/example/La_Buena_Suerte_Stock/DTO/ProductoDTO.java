package com.example.La_Buena_Suerte_Stock.DTO;

import com.example.La_Buena_Suerte_Stock.Enums.Ecategoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {
    private String nombre;
    private Double precio;
    private Integer stockActual;
    private Integer stockMinimo;
    private Ecategoria categoria;
    private String codigo;
}

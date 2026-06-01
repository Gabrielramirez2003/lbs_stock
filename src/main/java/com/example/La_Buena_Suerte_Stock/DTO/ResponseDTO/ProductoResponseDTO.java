package com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO;

import com.example.La_Buena_Suerte_Stock.Enums.Ecategoria;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private Integer stockActual;
    private Double precio;
    private Integer stockMinimo;
    private Ecategoria categoria;
    private Boolean activo;
}

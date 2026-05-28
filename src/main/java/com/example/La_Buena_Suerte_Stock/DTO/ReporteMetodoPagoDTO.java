package com.example.La_Buena_Suerte_Stock.DTO;

import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteMetodoPagoDTO {
    private EmetodoPago metodoPago;
    private Double total;
}
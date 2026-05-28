package com.example.La_Buena_Suerte_Stock.DTO;

import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VentaDTO {
    private EmetodoPago metodoPago;
    private List<DetalleDTO> detalles;
}

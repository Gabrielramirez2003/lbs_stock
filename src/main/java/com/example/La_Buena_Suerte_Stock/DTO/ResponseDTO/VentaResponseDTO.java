package com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO;

import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private Double total;
    private EmetodoPago metodoPago;
    private Long turnoId;
    private List<DetalleVentaResponseDTO> detalles;
}
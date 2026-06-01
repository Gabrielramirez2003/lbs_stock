package com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO;

import com.example.La_Buena_Suerte_Stock.Enums.EestadoTurno;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoResponseDTO {
    private Long id;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;
    private EestadoTurno estado;
    private Double totalVendido;
}
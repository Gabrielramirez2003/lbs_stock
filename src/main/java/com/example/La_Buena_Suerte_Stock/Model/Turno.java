package com.example.La_Buena_Suerte_Stock.Model;

import com.example.La_Buena_Suerte_Stock.Enums.EestadoTurno;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "turno")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaApertura;

    private LocalDateTime fechaCierre;

    @Enumerated(EnumType.STRING)
    private EestadoTurno estado;

    private double totalVendido;

    @OneToMany(mappedBy = "turno")
    @JsonIgnore
    private List<Venta> ventas;
}
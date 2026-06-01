package com.example.La_Buena_Suerte_Stock.Model;

import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    private double total;

    @Enumerated(EnumType.STRING)
    private EmetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "turno_id")
    @JsonIgnore
    private Turno turno;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;
}
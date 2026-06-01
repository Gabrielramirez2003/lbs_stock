package com.example.La_Buena_Suerte_Stock.Model;

import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="fecha_hora")
    private LocalDateTime fechaHora;

    private double total;
    private EmetodoPago metodoPago;



    @ManyToOne
    @JoinColumn(name = "turno_id")
    @JsonIgnore
    private Turno turno;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;
}

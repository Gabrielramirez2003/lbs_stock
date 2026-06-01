package com.example.La_Buena_Suerte_Stock.Service;


import com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO.TurnoResponseDTO;
import com.example.La_Buena_Suerte_Stock.Enums.EestadoTurno;
import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import com.example.La_Buena_Suerte_Stock.Model.Turno;
import com.example.La_Buena_Suerte_Stock.Model.Venta;
import com.example.La_Buena_Suerte_Stock.Repository.TurnoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;

    private TurnoResponseDTO toResponse(Turno turno) {
        return new TurnoResponseDTO(
                turno.getId(),
                turno.getFechaApertura(),
                turno.getFechaCierre(),
                turno.getEstado(),
                turno.getTotalVendido()
        );
    }

    public TurnoResponseDTO abrirTurno() {
        Optional<Turno> turnoAbierto = turnoRepository.findByEstado(EestadoTurno.ABIERTO);

        if (turnoAbierto.isPresent()) {
            throw new RuntimeException("Ya existe un turno abierto. Debe cerrarlo antes de abrir otro.");
        }

        Turno turno = Turno.builder()
                .fechaApertura(LocalDateTime.now())
                .estado(EestadoTurno.ABIERTO)
                .totalVendido(0.0)
                .build();

        return toResponse(turnoRepository.save(turno));
    }

    public TurnoResponseDTO cerrarTurno(Long idTurno) {
        Turno turno = buscarEntidadPorId(idTurno);

        if (turno.getEstado() == EestadoTurno.CERRADO) {
            throw new RuntimeException("El turno ya está cerrado");
        }

        double total = turno.getVentas()
                .stream()
                .mapToDouble(Venta::getTotal)
                .sum();

        turno.setFechaCierre(LocalDateTime.now());
        turno.setTotalVendido(total);
        turno.setEstado(EestadoTurno.CERRADO);

        return toResponse(turnoRepository.save(turno));
    }

    public Turno buscarEntidadPorId(Long idTurno) {
        return turnoRepository.findById(idTurno)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
    }

    public TurnoResponseDTO buscarXid(Long idTurno) {
        return toResponse(buscarEntidadPorId(idTurno));
    }

    public Turno buscarTurnoAbiertoEntidad() {
        return turnoRepository.findByEstado(EestadoTurno.ABIERTO)
                .orElseThrow(() -> new RuntimeException("No hay turno abierto"));
    }

    public List<TurnoResponseDTO> mostrarTodos() {
        return turnoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public double calcularTotalTurno(Long idTurno) {
        Turno turno = buscarEntidadPorId(idTurno);

        return turno.getVentas()
                .stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public Map<EmetodoPago, Double> obtenerResumenMetodoPago(Long turnoId) {
        Turno turno = buscarEntidadPorId(turnoId);

        return turno.getVentas()
                .stream()
                .collect(Collectors.groupingBy(
                        Venta::getMetodoPago,
                        Collectors.summingDouble(Venta::getTotal)
                ));
    }
}
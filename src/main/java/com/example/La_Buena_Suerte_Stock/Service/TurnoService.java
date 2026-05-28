package com.example.La_Buena_Suerte_Stock.Service;

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


    public Turno abrirTurno(){

        Optional<Turno> turnoAbierto =
                turnoRepository.findByEstado(EestadoTurno.ABIERTO);

        if (turnoAbierto.isPresent()) {
            throw new RuntimeException(
                    "Ya existe un turno abierto. Debe cerrarlo antes de abrir otro."
            );
        }

        Turno turno = Turno.builder()
                .fechaApertura(LocalDateTime.now())
                .estado(EestadoTurno.ABIERTO)
                .totalVendido(0.0)
                .build();

        return turnoRepository.save(turno);
    }

    public Turno cerrarTurno(int idTurno){
        Turno turno = buscarXid(idTurno);

        if (turno.getEstado() == EestadoTurno.CERRADO) {
            throw new RuntimeException("El turno ya está cerrado");
        }

        Double total = turno.getVentas()
                .stream()
                .mapToDouble(Venta::getTotal)
                .sum();

        turno.setFechaCierre(LocalDateTime.now());
        turno.setTotalVendido(total);
        turno.setEstado(EestadoTurno.CERRADO);

        return turnoRepository.save(turno);
    }

    public Turno buscarXid(int idTurno){
        return turnoRepository.findById(idTurno)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
    }

    public Turno buscarTurnoAbierto() {
        return turnoRepository.findByEstado(EestadoTurno.ABIERTO)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
    }


    public List<Turno> mostrarTodos(){
        return turnoRepository.findAll();
    }

    public double calcularTotalTurno(int idTurno){
        Turno turno = turnoRepository.findById(idTurno)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        return turno.getVentas()
                .stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public Map<EmetodoPago, Double> obtenerResumenMetodoPago(int turnoId) {

        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        return turno.getVentas()
                .stream()
                .collect(Collectors.groupingBy(
                        Venta::getMetodoPago,
                        Collectors.summingDouble(Venta::getTotal)
                ));
    }
}

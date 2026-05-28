package com.example.La_Buena_Suerte_Stock.Controller;

import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import com.example.La_Buena_Suerte_Stock.Model.Turno;
import com.example.La_Buena_Suerte_Stock.Service.TurnoService;
import jakarta.persistence.Embeddable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @PostMapping("/abrir")
    public Turno abrirTurno() {
        return turnoService.abrirTurno();
    }

    @PutMapping("/{id}/cerrar")
    public Turno cerrarTurno(@PathVariable int id) {
        return turnoService.cerrarTurno(id);
    }

    @GetMapping
    public List<Turno> listar() {
        return turnoService.mostrarTodos();
    }

    @GetMapping("/{id}")
    public Turno buscarPorId(@PathVariable int id) {
        return turnoService.buscarXid(id);
    }

    @GetMapping("/{id}/resumen-pagos")
    public Map<EmetodoPago, Double> resumenMetodoPago(@PathVariable int id) {
        return turnoService.obtenerResumenMetodoPago(id);
    }
}
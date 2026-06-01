package com.example.La_Buena_Suerte_Stock.Controller;


import com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO.TurnoResponseDTO;
import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import com.example.La_Buena_Suerte_Stock.Service.TurnoService;
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
    public TurnoResponseDTO abrirTurno() {
        return turnoService.abrirTurno();
    }

    @PutMapping("/{id}/cerrar")
    public TurnoResponseDTO cerrarTurno(@PathVariable Long id) {
        return turnoService.cerrarTurno(id);
    }

    @GetMapping
    public List<TurnoResponseDTO> listar() {
        return turnoService.mostrarTodos();
    }

    @GetMapping("/{id}")
    public TurnoResponseDTO buscarPorId(@PathVariable Long id) {
        return turnoService.buscarXid(id);
    }

    @GetMapping("/{id}/resumen-pagos")
    public Map<EmetodoPago, Double> resumenMetodoPago(@PathVariable Long id) {
        return turnoService.obtenerResumenMetodoPago(id);
    }
}
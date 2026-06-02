package com.example.La_Buena_Suerte_Stock.Controller;

import com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO.VentaResponseDTO;
import com.example.La_Buena_Suerte_Stock.DTO.VentaDTO;

import com.example.La_Buena_Suerte_Stock.Service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public VentaResponseDTO registrarVenta(@RequestBody VentaDTO venta) {
        return ventaService.registrarVenta(venta);
    }

    @GetMapping
    public List<VentaResponseDTO> listar() {
        return ventaService.listarVentas();
    }

    @GetMapping("/{id}")
    public VentaResponseDTO buscarPorId(@PathVariable Long id) {
        return ventaService.buscarXid(id);
    }

    @GetMapping("/turno/{turnoId}")
    public ResponseEntity<List<VentaResponseDTO>> ventasPorTurno(@PathVariable Long turnoId) {
        return ResponseEntity.ok(ventaService.obtenerVentasPorTurno(turnoId));
    }
}
package com.example.La_Buena_Suerte_Stock.Controller;

import com.example.La_Buena_Suerte_Stock.DTO.VentaDTO;
import com.example.La_Buena_Suerte_Stock.Model.Venta;
import com.example.La_Buena_Suerte_Stock.Service.PdfService;
import com.example.La_Buena_Suerte_Stock.Service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {


    private final VentaService ventaService;
    private final PdfService pdfService;

    @PostMapping
    public Venta registrarVenta(@RequestBody VentaDTO venta) {
        return ventaService.registrarVenta(venta);
    }

    @GetMapping
    public List<Venta> listar() {
        return ventaService.listarVentas();
    }

    @GetMapping("/{id}")
    public Venta buscarPorId(@PathVariable int id) {
        return ventaService.buscarXid(id);
    }

    @GetMapping("/turno/{turnoId}")
    public List<Venta> ventasPorTurno(@PathVariable int turnoId) {
        return ventaService.obtenerVentasPorTurno(turnoId);
    }


}

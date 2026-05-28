package com.example.La_Buena_Suerte_Stock.Service;

import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import com.example.La_Buena_Suerte_Stock.Model.Producto;
import com.example.La_Buena_Suerte_Stock.Model.Turno;
import com.example.La_Buena_Suerte_Stock.Model.Venta;
import com.example.La_Buena_Suerte_Stock.Repository.ProductoRepository;
import com.example.La_Buena_Suerte_Stock.Repository.TurnoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReporteService {

    private final TurnoRepository turnoRepository;
    private final ProductoRepository productoRepository;
    private final TurnoService turnoService;
    public String generarReporteTurno(int turnoId) {

        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));

        StringBuilder reporte = new StringBuilder();

        reporte.append("REPORTE DEL TURNO\n");
        reporte.append("Apertura: ").append(turno.getFechaApertura()).append("\n");
        reporte.append("Cierre: ").append(turno.getFechaCierre()).append("\n\n");

        for (Venta venta : turno.getVentas()) {

            reporte.append("Venta ID: ").append(venta.getId()).append("\n");
            reporte.append("Método de pago: ").append(venta.getMetodoPago()).append("\n");
            reporte.append("Total: $").append(venta.getTotal()).append("\n\n");
        }

        return reporte.toString();
    }

    public List<Producto> obtenerProductosFaltantes() {

        return productoRepository.findAll()
                .stream()
                .filter(producto ->
                        producto.getStockActual() <= producto.getStockMinimo())
                .toList();
    }

    public Map<String, List<Producto>> agruparFaltantesPorCategoria() {

        return obtenerProductosFaltantes()
                .stream()
                .collect(Collectors.groupingBy(
                        producto -> producto.getCategoria().toString()
                ));
    }

    public String generarResumenVentas(int turnoId) {

        Map<EmetodoPago, Double> resumen =
                turnoService.obtenerResumenMetodoPago(turnoId);

        StringBuilder texto = new StringBuilder();

        texto.append("RESUMEN DE VENTAS\n\n");

        resumen.forEach((metodo, total) -> {
            texto.append(metodo)
                    .append(": $")
                    .append(total)
                    .append("\n");
        });

        return texto.toString();
    }
}

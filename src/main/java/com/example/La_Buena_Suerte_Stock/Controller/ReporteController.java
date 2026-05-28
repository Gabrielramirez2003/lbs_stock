package com.example.La_Buena_Suerte_Stock.Controller;

import com.example.La_Buena_Suerte_Stock.Model.Producto;
import com.example.La_Buena_Suerte_Stock.Service.PdfService;
import com.example.La_Buena_Suerte_Stock.Service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;
    private final PdfService pdfService;

    @GetMapping("/turno/{turnoId}")
    public String reporteTurno(@PathVariable int turnoId) {
        return reporteService.generarReporteTurno(turnoId);
    }

    @GetMapping("/turnos/{turnoId}/resumen")
    public String resumenVentas(@PathVariable int turnoId) {
        return reporteService.generarResumenVentas(turnoId);
    }

    @GetMapping("/faltantes")
    public List<Producto> productosFaltantes() {
        return reporteService.obtenerProductosFaltantes();
    }

    @GetMapping("/faltantes/pdf")
    public ResponseEntity<byte[]> pdfFaltantes() {
        byte[] pdf = pdfService.generarPdfFaltantes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=faltantes.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/turno/{turnoId}/pdf")
    public ResponseEntity<byte[]> pdfTurno(@PathVariable int turnoId) {
        byte[] pdf = pdfService.generarPdfReporteTurno(turnoId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-turno.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}

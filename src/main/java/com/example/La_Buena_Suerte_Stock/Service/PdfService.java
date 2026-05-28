package com.example.La_Buena_Suerte_Stock.Service;

import com.example.La_Buena_Suerte_Stock.Model.Producto;
import com.example.La_Buena_Suerte_Stock.Model.Turno;
import com.example.La_Buena_Suerte_Stock.Model.Venta;
import com.example.La_Buena_Suerte_Stock.Repository.TurnoRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.itextpdf.layout.Document;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PdfService {

    private final ReporteService reporteService;
    private final TurnoRepository turnoRepository;

    public byte[] generarPdfFaltantes() {

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("REPORTE DE PRODUCTOS FALTANTES"));

            Map<String, List<Producto>> faltantes =
                    reporteService.agruparFaltantesPorCategoria();

            for (String categoria : faltantes.keySet()) {

                document.add(new Paragraph("\nCategoría: " + categoria));

                for (Producto producto : faltantes.get(categoria)) {

                    document.add(new Paragraph(
                            producto.getNombre()
                                    + " | Stock actual: "
                                    + producto.getStockActual()
                    ));
                }
            }

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException("Error al generar PDF");
        }
    }

    public byte[] generarPdfReporteTurno(int turnoId) {

        try {

            Turno turno = turnoRepository.findById(turnoId)
                    .orElseThrow(() ->
                            new RuntimeException("Turno no encontrado"));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("REPORTE DEL TURNO"));

            document.add(new Paragraph(
                    "Apertura: " + turno.getFechaApertura()
            ));

            document.add(new Paragraph(
                    "Cierre: " + turno.getFechaCierre()
            ));

            document.add(new Paragraph("\nVENTAS\n"));

            for (Venta venta : turno.getVentas()) {

                document.add(new Paragraph(
                        "Venta ID: " + venta.getId()
                                + " | Método: " + venta.getMetodoPago()
                                + " | Total: $" + venta.getTotal()
                ));
            }

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException("Error al generar PDF");
        }
    }
}

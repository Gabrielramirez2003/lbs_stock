package com.example.La_Buena_Suerte_Stock.Service;

import com.example.La_Buena_Suerte_Stock.Model.DetalleVenta;
import com.example.La_Buena_Suerte_Stock.Model.Producto;
import com.example.La_Buena_Suerte_Stock.Model.Turno;
import com.example.La_Buena_Suerte_Stock.Model.Venta;
import com.example.La_Buena_Suerte_Stock.Repository.TurnoRepository;
import com.example.La_Buena_Suerte_Stock.Repository.VentaRepository;
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
    private final VentaRepository ventaRepository;

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

    public byte[] generarReciboVenta(int ventaId) {

        try {

            Venta venta = ventaRepository.findById(ventaId)
                    .orElseThrow(() ->
                            new RuntimeException("Venta no encontrada"));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("RECIBO NO FISCAL"));

            document.add(new Paragraph(
                    "Venta N°: " + venta.getId()
            ));

            document.add(new Paragraph(
                    "Fecha: " + venta.getFechaHora()
            ));

            document.add(new Paragraph(
                    "Método de pago: " + venta.getMetodoPago()
            ));

            document.add(new Paragraph("\nPRODUCTOS\n"));

            for (DetalleVenta detalle : venta.getDetalles()) {

                document.add(new Paragraph(
                        detalle.getProducto().getNombre()
                                + " | Cant: " + detalle.getCantidad()
                                + " | Unit: $" + detalle.getPrecioUnitario()
                ));
            }

            document.add(new Paragraph(
                    "\nTOTAL: $" + venta.getTotal()
            ));

            document.add(new Paragraph(
                    "\nComprobante interno sin validez fiscal."
            ));

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException("Error al generar recibo");
        }
    }
}

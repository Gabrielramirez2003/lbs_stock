package com.example.La_Buena_Suerte_Stock.Service;

import com.example.La_Buena_Suerte_Stock.DTO.DetalleDTO;
import com.example.La_Buena_Suerte_Stock.DTO.VentaDTO;
import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import com.example.La_Buena_Suerte_Stock.Model.DetalleVenta;
import com.example.La_Buena_Suerte_Stock.Model.Producto;
import com.example.La_Buena_Suerte_Stock.Model.Turno;
import com.example.La_Buena_Suerte_Stock.Model.Venta;
import com.example.La_Buena_Suerte_Stock.Repository.ProductoRepository;
import com.example.La_Buena_Suerte_Stock.Repository.TurnoRepository;
import com.example.La_Buena_Suerte_Stock.Repository.VentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VentaService {
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final TurnoService turnoService;

    private DetalleVenta toDetalleEntity(DetalleDTO dto) {

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        DetalleVenta detalle = new DetalleVenta();

        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());

        return detalle;
    }
    private Venta DTOaEntidad(VentaDTO dto){
        Venta venta = new Venta();

        venta.setMetodoPago(dto.getMetodoPago());

        List<DetalleVenta> detalles = dto.getDetalles()
                .stream()
                .map(this::toDetalleEntity)
                .toList();

        venta.setDetalles(detalles);

        detalles.forEach(detalle -> detalle.setVenta(venta));

        return venta;
    }
    public Venta registrarVenta(VentaDTO dto) {

        Venta venta = DTOaEntidad(dto);

        Double total = calcularTotal(venta.getDetalles());
        venta.setTotal(total);
        venta.setFechaHora(LocalDateTime.now());

        Turno turno = turnoService.buscarTurnoAbierto();
        venta.setTurno(turno);

        descontarStock(venta.getDetalles());

        return ventaRepository.save(venta);
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Venta buscarXid(int id){
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el venta con el id: " + id));
    }

    public void eliminarVenta(int id){
        Venta venta  = buscarXid(id);
        ventaRepository.delete(venta);
    }

    public double calcularTotal(List<DetalleVenta> detalles){
        return detalles.stream()
                .mapToDouble(detalle ->
                        detalle.getPrecioUnitario() * detalle.getCantidad())
                .sum();
    }


    public void descontarStock(List<DetalleVenta> detalles) {

        for (DetalleVenta detalle : detalles) {

            Producto producto = detalle.getProducto();

            producto.setStockActual(
                    producto.getStockActual() - detalle.getCantidad()
            );

            productoRepository.save(producto);
        }
    }

    public List<Venta> obtenerVentasPorTurno(int idTurno){
        List<Venta> ventas = ventaRepository.findAll()
                .stream()
                .filter(v -> v.getTurno().getId() == idTurno)
                .toList();

        return ventas;
    }

    public List<Venta> obtenerVentasPorMetodoPago(EmetodoPago metodoPago){
        List<Venta> ventas = ventaRepository.findAll()
                .stream()
                .filter(v -> v.getMetodoPago().equals(metodoPago))
                .toList();

        return ventas;
    }



}

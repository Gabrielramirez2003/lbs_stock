package com.example.La_Buena_Suerte_Stock.Service;

import com.example.La_Buena_Suerte_Stock.DTO.*;
import com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO.DetalleVentaResponseDTO;
import com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO.VentaResponseDTO;
import com.example.La_Buena_Suerte_Stock.Enums.EmetodoPago;
import com.example.La_Buena_Suerte_Stock.Model.*;
import com.example.La_Buena_Suerte_Stock.Repository.ProductoRepository;
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
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!producto.getActivo()) {
            throw new RuntimeException("El producto está dado de baja: " + producto.getNombre());
        }

        if (producto.getStockActual() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
        }

        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio() * dto.getCantidad());

        return detalle;
    }

    private Venta DTOaEntidad(VentaDTO dto) {
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

    private VentaResponseDTO toResponse(Venta venta) {
        List<DetalleVentaResponseDTO> detalles = venta.getDetalles()
                .stream()
                .map(d -> new DetalleVentaResponseDTO(
                        d.getProducto().getId(),
                        d.getProducto().getNombre(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal()
                ))
                .toList();

        return new VentaResponseDTO(
                venta.getId(),
                venta.getFechaHora(),
                venta.getTotal(),
                venta.getMetodoPago(),
                venta.getTurno().getId(),
                detalles
        );
    }

    public VentaResponseDTO registrarVenta(VentaDTO dto) {
        Venta venta = DTOaEntidad(dto);

        double total = calcularTotal(venta.getDetalles());
        venta.setTotal(total);
        venta.setFechaHora(LocalDateTime.now());

        Turno turno = turnoService.buscarTurnoAbiertoEntidad();
        venta.setTurno(turno);

        descontarStock(venta.getDetalles());

        return toResponse(ventaRepository.save(venta));
    }

    public List<VentaResponseDTO> listarVentas() {
        return ventaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Venta buscarEntidadPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la venta con el id: " + id));
    }

    public VentaResponseDTO buscarXid(Long id) {
        return toResponse(buscarEntidadPorId(id));
    }

    public double calcularTotal(List<DetalleVenta> detalles) {
        return detalles.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
    }

    public void descontarStock(List<DetalleVenta> detalles) {
        for (DetalleVenta detalle : detalles) {
            Producto producto = detalle.getProducto();

            if (producto.getStockActual() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            producto.setStockActual(producto.getStockActual() - detalle.getCantidad());
            productoRepository.save(producto);
        }
    }

    public List<VentaResponseDTO> obtenerVentasPorTurno(Long idTurno) {
        return ventaRepository.findAll()
                .stream()
                .filter(v -> v.getTurno().getId().equals(idTurno))
                .map(this::toResponse)
                .toList();
    }

    public List<VentaResponseDTO> obtenerVentasPorMetodoPago(EmetodoPago metodoPago) {
        return ventaRepository.findAll()
                .stream()
                .filter(v -> v.getMetodoPago().equals(metodoPago))
                .map(this::toResponse)
                .toList();
    }
}
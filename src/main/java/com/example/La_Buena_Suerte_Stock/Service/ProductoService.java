package com.example.La_Buena_Suerte_Stock.Service;

import com.example.La_Buena_Suerte_Stock.DTO.ProductoDTO;

import com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO.ProductoResponseDTO;
import com.example.La_Buena_Suerte_Stock.Model.Producto;
import com.example.La_Buena_Suerte_Stock.Repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    private Producto DTOaEntidad(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setStockActual(dto.getStockActual());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setActivo(true);
        return producto;
    }

    private ProductoResponseDTO toResponse(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getCodigo(),
                producto.getNombre(),
                producto.getStockActual(),
                producto.getPrecio(),
                producto.getStockMinimo(),
                producto.getCategoria(),
                producto.getActivo()
        );
    }

    public ProductoResponseDTO createProducto(ProductoDTO dto) {
        Producto producto = DTOaEntidad(dto);
        return toResponse(productoRepository.save(producto));
    }

    public Producto buscarEntidadPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el producto con el id: " + id));
    }

    public ProductoResponseDTO buscarXid(Long id) {
        return toResponse(buscarEntidadPorId(id));
    }

    public List<ProductoResponseDTO> mostrarTodos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductoResponseDTO modificarProducto(Long id, ProductoDTO dto) {
        Producto producto = buscarEntidadPorId(id);

        producto.setCodigo(dto.getCodigo());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setStockActual(dto.getStockActual());
        producto.setStockMinimo(dto.getStockMinimo());

        return toResponse(productoRepository.save(producto));
    }

    public void eliminarProducto(Long id) {
        Producto producto = buscarEntidadPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    public List<ProductoResponseDTO> obtenerFaltantes() {
        return productoRepository.findByActivoTrue()
                .stream()
                .filter(p -> p.getStockActual() <= p.getStockMinimo())
                .map(this::toResponse)
                .toList();
    }
}
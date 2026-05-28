package com.example.La_Buena_Suerte_Stock.Service;

import com.example.La_Buena_Suerte_Stock.DTO.ProductoDTO;
import com.example.La_Buena_Suerte_Stock.Model.Producto;
import com.example.La_Buena_Suerte_Stock.Repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    private Producto DTOaEntidad(ProductoDTO dto){
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(dto.getCategoria());
        producto.setStockActual(dto.getStockActual());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setCodigo(dto.getCodigo());

        return producto;
    }
    public Producto createProducto(ProductoDTO dto) {
        Producto producto = DTOaEntidad(dto);
        return productoRepository.save(producto);
    }

    public Producto buscarXid(int id) {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el producto con el id: " + id));
        return producto;
    }

    public List<Producto> mostratTodos(){
        return productoRepository.findAll();
    }

    public Producto modificarProducto(int id, ProductoDTO dto) {
        Producto producto = DTOaEntidad(dto);
        Producto p = buscarXid(id);

        p.setNombre(producto.getNombre());
        p.setPrecio(producto.getPrecio());
        p.setCategoria(producto.getCategoria());
        p.setStockActual(producto.getStockActual());
        p.setStockMinimo(producto.getStockMinimo());

        return productoRepository.save(p);
    }

    public void eliminarProducto(int id) {
        Producto producto = buscarXid(id);

        productoRepository.delete(producto);
    }

    public List<Producto> obtenerFaltantes() {
        return productoRepository.findAll()
                .stream()
                .filter(p -> p.getStockActual()<= p.getStockMinimo())
                .toList();
    }
}

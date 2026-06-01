package com.example.La_Buena_Suerte_Stock.Controller;

import com.example.La_Buena_Suerte_Stock.DTO.ProductoDTO;

import com.example.La_Buena_Suerte_Stock.DTO.ResponseDTO.ProductoResponseDTO;
import com.example.La_Buena_Suerte_Stock.Service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ProductoResponseDTO guardar(@RequestBody ProductoDTO dto) {
        return productoService.createProducto(dto);
    }

    @GetMapping
    public List<ProductoResponseDTO> listar() {
        return productoService.mostrarTodos();
    }

    @GetMapping("/{id}")
    public ProductoResponseDTO buscarPorId(@PathVariable Long id) {
        return productoService.buscarXid(id);
    }

    @PutMapping("/{id}")
    public ProductoResponseDTO actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return productoService.modificarProducto(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    @GetMapping("/faltantes")
    public List<ProductoResponseDTO> obtenerFaltantes() {
        return productoService.obtenerFaltantes();
    }
}
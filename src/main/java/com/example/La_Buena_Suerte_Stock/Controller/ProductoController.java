package com.example.La_Buena_Suerte_Stock.Controller;

import com.example.La_Buena_Suerte_Stock.DTO.ProductoDTO;
import com.example.La_Buena_Suerte_Stock.Model.Producto;
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
    public Producto guardar(@RequestBody ProductoDTO dto) {
        return productoService.createProducto(dto);
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.mostratTodos();
    }

    @GetMapping("/{id}")
    public Producto buscarPorId(@PathVariable int id) {
        return productoService.buscarXid(id);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable int id, @RequestBody ProductoDTO dto) {
        return productoService.modificarProducto(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        productoService.eliminarProducto(id);
    }

    @GetMapping("/faltantes")
    public List<Producto> obtenerFaltantes() {
        return productoService.obtenerFaltantes();
    }

}
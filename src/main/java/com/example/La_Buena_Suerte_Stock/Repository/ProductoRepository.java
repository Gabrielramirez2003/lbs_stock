package com.example.La_Buena_Suerte_Stock.Repository;

import com.example.La_Buena_Suerte_Stock.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}

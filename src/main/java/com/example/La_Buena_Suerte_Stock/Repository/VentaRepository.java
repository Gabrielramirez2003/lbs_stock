package com.example.La_Buena_Suerte_Stock.Repository;

import com.example.La_Buena_Suerte_Stock.Enums.EestadoTurno;
import com.example.La_Buena_Suerte_Stock.Model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
}

package com.example.La_Buena_Suerte_Stock.Repository;

import com.example.La_Buena_Suerte_Stock.Enums.EestadoTurno;
import com.example.La_Buena_Suerte_Stock.Model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    Optional<Turno> findByEstado(EestadoTurno estado);
}

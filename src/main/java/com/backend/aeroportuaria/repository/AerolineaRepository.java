package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Aerolinea;
import com.backend.aeroportuaria.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AerolineaRepository extends JpaRepository<Aerolinea, Integer> {

    Optional<Aerolinea> findByNombre(String nombre); //Después de findBy se pone el nombre del atributo por el cual se quiere buscar

    boolean existsByNombre(String nombre); //Después de existsBy se pone el nombre del atributo por el cual se quiere buscar

}

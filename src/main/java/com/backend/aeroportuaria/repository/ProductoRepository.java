package com.backend.aeroportuaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.aeroportuaria.entity.Producto;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findByNombre(String nombre); //Después de findBy se pone el nombre del atributo por el cual se quiere buscar

    boolean existsByNombre(String nombre); //Después de existsBy se pone el nombre del atributo por el cual se quiere buscar
}

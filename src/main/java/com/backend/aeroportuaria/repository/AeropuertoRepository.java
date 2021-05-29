package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, String> {

    Optional<Aeropuerto> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}

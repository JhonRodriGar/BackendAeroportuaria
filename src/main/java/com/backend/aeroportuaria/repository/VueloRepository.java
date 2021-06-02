package com.backend.aeroportuaria.repository;

import com.backend.aeroportuaria.entity.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VueloRepository extends JpaRepository<Vuelo, String> {

}

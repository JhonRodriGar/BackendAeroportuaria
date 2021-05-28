package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Aerolinea;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AerolineaService {

    public List<Aerolinea> list();

    public Optional<Aerolinea> getOne(int id);

    public Optional<Aerolinea> getByNombre(String nombre);

    public void  save(Aerolinea aerolinea);

    public void delete(int id);

    public boolean existsById(int id);

    public boolean existsByNombre(String nombre);

    /*
    Código original de ProductoService

    public List<Producto> list();

    public Optional<Producto> getOne(int id);

    public Optional<Producto> getByNombre(String nombre);

    public void  save(Producto producto);

    public void delete(int id);

    public boolean existsById(int id);

    public boolean existsByNombre(String nombre);
     */

}

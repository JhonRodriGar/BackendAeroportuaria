package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    public List<Producto> list();

    public Optional<Producto> getOne(int id);

    public Optional<Producto> getByNombre(String nombre);

    public void  save(Producto producto);

    public void delete(int id);

    public boolean existsById(int id);

    public boolean existsByNombre(String nombre);
}

package com.backend.aeroportuaria.service;

import com.backend.aeroportuaria.entity.Empleado;
import com.backend.aeroportuaria.entity.Vuelo;

import java.util.List;
import java.util.Optional;

public interface VueloService {

    public List<Vuelo> list();

    public Optional<Vuelo> getOne(String id);

    public void  save(Vuelo vuelo);

    public void delete(String id);

    public boolean existsById(String id);

}

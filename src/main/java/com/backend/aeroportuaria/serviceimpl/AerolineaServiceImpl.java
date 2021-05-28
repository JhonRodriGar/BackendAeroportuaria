package com.backend.aeroportuaria.serviceimpl;

import com.backend.aeroportuaria.entity.Aerolinea;
import com.backend.aeroportuaria.repository.AerolineaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AerolineaServiceImpl {

    @Autowired
    AerolineaRepository aerolineaRepository;

    public List<Aerolinea> list(){
        return aerolineaRepository.findAll();
    }


    public Optional<Aerolinea> getOne(int id){
        return aerolineaRepository.findById(id);
    }

    public Optional<Aerolinea> getByNombre(String nombre){
        return aerolineaRepository.findByNombre(nombre);
    }

    public void  save(Aerolinea aerolinea){
        aerolineaRepository.save(aerolinea);
    }

    public void delete(int id){
        aerolineaRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return aerolineaRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return aerolineaRepository.existsByNombre(nombre);
    }

}

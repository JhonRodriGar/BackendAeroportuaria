package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.AeropuertoRequest;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.entity.Aerolinea;
import com.backend.aeroportuaria.entity.Aeropuerto;
import com.backend.aeroportuaria.serviceimpl.AerolineaServiceImpl;
import com.backend.aeroportuaria.serviceimpl.AeropuertoServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/aeropuerto")
@CrossOrigin("*")
public class AeropuertoController {

    @Autowired
    AeropuertoServiceImpl aeropuertoService;

    @Autowired
    AerolineaServiceImpl aerolineaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Aeropuerto>> list(){
        List<Aeropuerto> list = aeropuertoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Aeropuerto> getById(@PathVariable("id") String id){
        if(!aeropuertoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Aeropuerto aeropuerto = aeropuertoService.getOne(id).get();
        return new ResponseEntity(aeropuerto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Aeropuerto> getByNombre(@PathVariable("nombre") String nombre){
        if(!aeropuertoService.existsByNombre(nombre))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Aeropuerto aeropuerto = aeropuertoService.getByNombre(nombre).get();
        return new ResponseEntity(aeropuerto, HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('ADMIN')OR hasRole('PROVEEDOR')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AeropuertoRequest aeropuertoRequest){

        if(StringUtils.isBlank(aeropuertoRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(3, "El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(aeropuertoService.existsByNombre(aeropuertoRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(5, "Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(aeropuertoRequest.getId_ciudad()))
            return new ResponseEntity(new ResponseCode(15, "En esa ciudad ya hay un aeropuerto internacional"), HttpStatus.BAD_REQUEST);

        //Si en las anteriores validaciones está bien entonces crea el aeropuerto.

        Aeropuerto aeropuerto = new Aeropuerto(aeropuertoRequest.getIdAeropuerto(), aeropuertoRequest.getNombre(),
                aeropuertoRequest.getEstado(), aeropuertoRequest.getPais(), aeropuertoRequest.getId_ciudad());

        //Se asigna las Aerolineas al Aeropuerto.

        Set<Aerolinea> aerolineas = new HashSet<>();

        for (int i = 0; i < aeropuertoRequest.getAerolineas().size(); i++) {
            aerolineas.add(aerolineaService.getOne(aeropuertoRequest.getAerolineas().get(i)).get());
        }

        aeropuerto.setAerolineas(aerolineas);

        aeropuertoService.save(aeropuerto);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN') OR hasRole('VENDEDOR')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AeropuertoRequest aeropuertoRequest){
        if(!aeropuertoService.existsById(aeropuertoRequest.getIdAeropuerto()))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(aeropuertoRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(3, "El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        Aeropuerto aeropuerto = aeropuertoService.getOne(aeropuertoRequest.getIdAeropuerto()).get();
        aeropuerto.setIdAeropuerto(aeropuertoRequest.getIdAeropuerto());
        aeropuerto.setNombre(aeropuertoRequest.getNombre());
        aeropuerto.setEstado(aeropuertoRequest.getEstado());
        aeropuerto.setPais(aeropuertoRequest.getPais());
        aeropuerto.setId_ciudad(aeropuertoRequest.getId_ciudad());

        Set<Aerolinea> aerolineas = new HashSet<>();

        for (int i = 0; i < aeropuertoRequest.getAerolineas().size(); i++) {
            aerolineas.add(aerolineaService.getOne(aeropuertoRequest.getAerolineas().get(i)).get());
        }

        aeropuerto.setAerolineas(aerolineas);

        aeropuertoService.save(aeropuerto);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        if(!aeropuertoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "La Aerolínea no existe"), HttpStatus.NOT_FOUND);
        aeropuertoService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}

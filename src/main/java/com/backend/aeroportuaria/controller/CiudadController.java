package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.CiudadRequest;
import com.backend.aeroportuaria.dto.ProductoDto;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.entity.Ciudad;
import com.backend.aeroportuaria.entity.Producto;
import com.backend.aeroportuaria.service.CiudadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ciudad")
@CrossOrigin("*")
public class CiudadController {

    @Autowired
    CiudadService ciudadService;

    //Se está creando un bucle en el reporte pendiente CORREGIR

    @GetMapping("/lista")
    public ResponseEntity<List<Ciudad>> list(){
        List<Ciudad> list = ciudadService.list();
/*
        System.out.println("La lista en la posición o es: " + list.get(0).getId_ciudad());

        List<CiudadRequest> ciudadResponse =  new ArrayList<>();

        ciudadResponse.get(0).setId_ciudad("Prueba seteo");

        System.out.println("El tamaño de la lista es : " + list.size());

        for (int i = 0; i < list.size(); i++) {

            System.out.println("Probando " + ciudadResponse.get(0));

        }
*/
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") String id){
        if(!ciudadService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Ciudad ciudad = ciudadService.getOne(id).get();
        return new ResponseEntity(ciudad, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        if(!ciudadService.existsByNombre(nombre))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Ciudad ciudad = ciudadService.getByNombre(nombre).get();
        return new ResponseEntity(ciudad, HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('ADMIN')OR hasRole('PROVEEDOR')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CiudadRequest ciudadRequest){
        if(StringUtils.isBlank(ciudadRequest.getNombre())) //Valida si el nombre está en blanco
            return new ResponseEntity(new ResponseCode(3, "El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(ciudadService.existsByNombre(ciudadRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(5, "Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Ciudad ciudad = new Ciudad(ciudadRequest.getId_ciudad(), ciudadRequest.getNombre(), ciudadRequest.getEstado(),ciudadRequest.getPais());
        ciudadService.save(ciudad);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN') OR hasRole('VENDEDOR')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CiudadRequest ciudadRequest){
        if(!ciudadService.existsById(ciudadRequest.getId_ciudad()))
            return new ResponseEntity(new ResponseCode(2, "La ciudad no existe"), HttpStatus.NOT_FOUND);
        if(ciudadService.existsByNombre(ciudadRequest.getNombre()) && ciudadService.getByNombre(ciudadRequest.getNombre()).get().getId_ciudad() != ciudadRequest.getId_ciudad())
            return new ResponseEntity(new ResponseCode(7, "El nombre ingresado ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(ciudadRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(3, "El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        Ciudad ciudad = ciudadService.getOne(ciudadRequest.getId_ciudad()).get();
        ciudad.setId_ciudad(ciudadRequest.getId_ciudad());
        ciudad.setNombre(ciudadRequest.getNombre());
        ciudad.setEstado(ciudadRequest.getEstado());
        ciudad.setPais(ciudadRequest.getPais());

        ciudadService.save(ciudad);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")String id){
        if(!ciudadService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        ciudadService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}

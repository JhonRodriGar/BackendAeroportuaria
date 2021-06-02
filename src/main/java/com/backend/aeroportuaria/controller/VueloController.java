package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.ProductoDto;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.dto.VueloRequest;
import com.backend.aeroportuaria.entity.Aerolinea;
import com.backend.aeroportuaria.entity.Producto;
import com.backend.aeroportuaria.entity.Vuelo;
import com.backend.aeroportuaria.service.ProductoService;
import com.backend.aeroportuaria.service.VueloService;
import com.backend.aeroportuaria.serviceimpl.AerolineaServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/vuelo")
@CrossOrigin("*")
public class VueloController {

    @Autowired
    VueloService vueloService;

    @Autowired
    AerolineaServiceImpl aerolineaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Vuelo>> list(){
        List<Vuelo> list = vueloService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") String id){
        if(!vueloService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        Vuelo vuelo = vueloService.getOne(id).get();
        return new ResponseEntity(vuelo, HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('ADMIN')OR hasRole('PROVEEDOR')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody VueloRequest vueloRequest){
        if(!aerolineaService.existsById(vueloRequest.getIdAerolinea()))
            return new ResponseEntity(new ResponseCode(18, "No existe la Aerolínea ingresada"), HttpStatus.NOT_FOUND);

        Aerolinea aerolineaBd = aerolineaService.getOne(vueloRequest.getIdAerolinea()).get();

        Vuelo vuelo = new Vuelo(vueloRequest.getIdVuelo(), vueloRequest.getFuente(), vueloRequest.getDestino(), vueloRequest.getLlegada(), vueloRequest.getSalida(), vueloRequest.getEstado(), vueloRequest.getDuracion(), vueloRequest.getTipoVuelo(), vueloRequest.getNumeroParadas(), vueloRequest.getClase(), aerolineaBd);
        vueloService.save(vuelo);
        return new ResponseEntity(new ResponseCode(6, "Creado exitosamente"), HttpStatus.OK);

    }

    //@PreAuthorize("hasRole('ADMIN') OR hasRole('VENDEDOR')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody VueloRequest vueloRequest){
        if(!vueloService.existsById(vueloRequest.getIdVuelo()))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
/*
PENDIENTE CORREGIR
Cuando se realiza la búsqueda de vuelo en la base de datos hay un error, aparentemente se está creando un bucle
la petición retrorna que se actualizó pero en realidad en la BD sigue igual
Al tratar de imprimir por consola el vuelo sale error al parecer por agotar el espacio de memora debido al bucle
El bucle se genera porque la clase Vuelo tiene un atributo de la clase Aerolinea y a su vez esta última,
tiene una lista de buelos
 */
        Vuelo vuelo = vueloService.getOne(vueloRequest.getIdVuelo()).get();

        System.out.println("El vuelo encontrado en BD es: " + vuelo);

        vuelo.setIdVuelo(vuelo.getIdVuelo());
        vuelo.setFuente(vuelo.getFuente());
        vuelo.setDestino(vuelo.getDestino());
        vuelo.setLlegada(vuelo.getLlegada());
        vuelo.setSalida(vuelo.getSalida());
        vuelo.setEstado(vuelo.getEstado());
        vuelo.setDuracion(vuelo.getDuracion());
        vuelo.setTipoVuelo(vuelo.getTipoVuelo());
        vuelo.setNumeroParadas(vuelo.getNumeroParadas());
        vuelo.setClase(vuelo.getClase());

        Aerolinea aerolineaBd = aerolineaService.getOne(vueloRequest.getIdAerolinea()).get();

        vuelo.setAerolinea(aerolineaBd);

        vueloService.save(vuelo);
        return new ResponseEntity(new ResponseCode(8, "Actualizado exitosamente"), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        if(!vueloService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "No se encontró información con los datos ingresados"), HttpStatus.NOT_FOUND);
        vueloService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Eliminado exitosamente"), HttpStatus.OK);
    }

}

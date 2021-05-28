package com.backend.aeroportuaria.controller;

import com.backend.aeroportuaria.dto.AerolineaRequest;
import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.entity.Aerolinea;
import com.backend.aeroportuaria.service.AerolineaService;
import com.backend.aeroportuaria.service.ProductoService;
import com.backend.aeroportuaria.serviceimpl.AerolineaServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aerolinea")
@CrossOrigin("*")
public class AerolineaController {

    @Autowired
    AerolineaServiceImpl aerolineaService;

    @GetMapping("/lista")
    public ResponseEntity<List<Aerolinea>> list(){
        List<Aerolinea> list = aerolineaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Aerolinea> getById(@PathVariable("id") int id){
        if(!aerolineaService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "La Aerolinea no existe"), HttpStatus.NOT_FOUND);
        Aerolinea aerolinea = aerolineaService.getOne(id).get(); //Como getOne trae un opcional entonces debe poner get
        return new ResponseEntity(aerolinea, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Aerolinea> getByNombre(@PathVariable("nombre") String nombre){
        if(!aerolineaService.existsByNombre(nombre))
            return new ResponseEntity(new ResponseCode(2, "La Aerol+nea no existe"), HttpStatus.NOT_FOUND);
        Aerolinea aerolinea = aerolineaService.getByNombre(nombre).get();
        return new ResponseEntity(aerolinea, HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('ADMIN')OR hasRole('PROVEEDOR')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AerolineaRequest aerolineaRequest){
        if(StringUtils.isBlank(aerolineaRequest.getNombre())) //Valida si el nombre está en blanco
            return new ResponseEntity(new ResponseCode(3, "El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(aerolineaService.existsByNombre(aerolineaRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(5, "Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(aerolineaRequest.getCodigoTresDigitos())) //Valida si el nombre está en blanco
            return new ResponseEntity(new ResponseCode(4, "El código de Aerolínea es obligatorio"), HttpStatus.BAD_REQUEST);
        Aerolinea aerolinea = new Aerolinea(aerolineaRequest.getIdAerolinea(), aerolineaRequest.getNombre(), aerolineaRequest.getCodigoTresDigitos());
        aerolineaService.save(aerolinea);
        return new ResponseEntity(new ResponseCode(6, "Aerolínea creada"), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN') OR hasRole('VENDEDOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody AerolineaRequest aerolineaRequest){
        if(!aerolineaService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "La Aerolínea no existe"), HttpStatus.NOT_FOUND);
        if(aerolineaService.existsByNombre(aerolineaRequest.getNombre()) && aerolineaService.getByNombre(aerolineaRequest.getNombre()).get().getIdAerolinea() != id) //El nombre al actualizar no puede ser igual al de otro Aerolinea que ya exista, para eso valida que en la base de datos no hay otro Aerolinea con el mismo nombre y diferente id al que yo estoy ingresando
            return new ResponseEntity(new ResponseCode(7, "El nombre ingresado ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(aerolineaRequest.getNombre()))
            return new ResponseEntity(new ResponseCode(3, "El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        Aerolinea aerolinea = aerolineaService.getOne(id).get();
        aerolinea.setIdAerolinea(aerolineaRequest.getIdAerolinea());
        aerolinea.setNombre(aerolineaRequest.getNombre());
        aerolinea.setCodigoTresDigitos(aerolineaRequest.getCodigoTresDigitos());

        aerolineaService.save(aerolinea);
        return new ResponseEntity(new ResponseCode(8, "Aerolínea actualizada"), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!aerolineaService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "La Aerolínea no existe"), HttpStatus.NOT_FOUND);
        aerolineaService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Aerolinea eliminada"), HttpStatus.OK);
    }

}

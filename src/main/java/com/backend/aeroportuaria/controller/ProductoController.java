package com.backend.aeroportuaria.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.backend.aeroportuaria.dto.ResponseCode;
import com.backend.aeroportuaria.dto.ProductoDto;
import com.backend.aeroportuaria.entity.Producto;
import com.backend.aeroportuaria.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin("*")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        List<Producto> list = productoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "El producto no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getOne(id).get(); //Como getOne trae un opcional entonces debe poner get
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        if(!productoService.existsByNombre(nombre))
            return new ResponseEntity(new ResponseCode(2, "El producto no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getByNombre(nombre).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }
    
   // @PreAuthorize("hasRole('ADMIN')OR hasRole('PROVEEDOR')") //Roles autorizados para acceder a la petición de este método
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre())) //Valida si el nombre está en blanco
            return new ResponseEntity(new ResponseCode(3, "El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0 ) //Puede poner el condicional productoDto.getPrecio()==null porque el precio lo creo de la clase Float en la clase ProductoDto. También se podría haber hecho sin necesidad de definir el precio de la clase Float en la clase ProductoDto pero casteando en esta línea, pone (Float) al inicio de la condición.
            return new ResponseEntity(new ResponseCode(4, "El precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if(productoService.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new ResponseCode(5, "Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Producto producto = new Producto(productoDto.getNombre(), productoDto.getDescripcion(), productoDto.getPrecio(), productoDto.getCantidad());
        productoService.save(producto);
        return new ResponseEntity(new ResponseCode(6, "Producto creado"), HttpStatus.OK);
    }
    
    //@PreAuthorize("hasRole('ADMIN') OR hasRole('VENDEDOR')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody ProductoDto productoDto){
        if(!productoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "El producto no existe"), HttpStatus.NOT_FOUND);
        if(productoService.existsByNombre(productoDto.getNombre()) && productoService.getByNombre(productoDto.getNombre()).get().getId() != id) //El nombre al actualizar no puede ser igual al de otro producto que ya exista, para eso valida que en la base de datos no hay otro producto con el mismo nombre y diferente id al que yo estoy ingresando
            return new ResponseEntity(new ResponseCode(7, "El nombre ingresado ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new ResponseCode(3, "El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio()==null || productoDto.getPrecio()<0 )
            return new ResponseEntity(new ResponseCode(4, "El precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Producto producto = productoService.getOne(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setPrecio(productoDto.getPrecio());
        producto.setCantidad(productoDto.getCantidad());

        productoService.save(producto);
        return new ResponseEntity(new ResponseCode(8, "Producto actualizado"), HttpStatus.OK);
    }
    
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!productoService.existsById(id))
            return new ResponseEntity(new ResponseCode(2, "El producto no existe"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity(new ResponseCode(9, "Producto eliminado"), HttpStatus.OK);
    }
}

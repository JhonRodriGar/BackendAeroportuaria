package com.backend.aeroportuaria.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductoDto {

    @NotBlank //solo se usa para String, no puede ser nulo y tamaño> 0
    private String nombre;

    @NotBlank
    private String descripcion;

    @Min(0) //El precio puede ser 0 pero no un valor negativo
    private Float precio; //El precio no es un primitivo sino un valor de la clase Float, por eso Float aparece en mayúscula, se requiere para poder poner el condicional de no nullo en los métodos create y update en el controller

    @Min(0)
    private Integer cantidad;

    public ProductoDto() {
    }

    public ProductoDto(String nombre, String descripcion, @Min(0) Float precio, @Min(0) Integer cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

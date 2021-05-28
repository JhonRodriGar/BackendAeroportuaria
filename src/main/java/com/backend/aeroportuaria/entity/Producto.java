package com.backend.aeroportuaria.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Crea los set y get
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTOS") //Es el nombre de la tabla en la BD
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String descripcion;
    private float precio;
    private Integer cantidad;

    public Producto(String nombre, String descripcion, float precio, Integer cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}

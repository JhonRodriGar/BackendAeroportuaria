package com.backend.aeroportuaria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VUELO")
public class Vuelo {

    @Id
    @NotNull
    private String idVuelo;

    private String fuente;

    private String destino;

    private String llegada;

    private String salida;

    private String estado;

    private String duracion;

    private String tipoVuelo;

    private int numeroParadas;

    private String clase;

    //private String aerolinea;

    //Crea relación con la tabla Aerolínea, muchos Vuelos pueden pertenecer a una Aerolínea
    //Referencia tomada de https://www.oscarblancarteblog.com/2018/12/20/relaciones-onetomany/
    @ManyToOne
    @JoinColumn(name = "ID_AEROLINEA", nullable = false, updatable = false)
    private Aerolinea aerolinea;

}

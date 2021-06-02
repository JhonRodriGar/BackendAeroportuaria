package com.backend.aeroportuaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueloRequest {

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

    private String idAerolinea;

}

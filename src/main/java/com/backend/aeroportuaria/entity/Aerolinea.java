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
@Table(name = "AEROLINEA") //No estaba
public class Aerolinea {

    @Id
    @NotNull
    private String idAerolinea;

    private String nombre;

    private String codigoTresDigitos;

}

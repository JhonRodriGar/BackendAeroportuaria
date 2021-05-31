package com.backend.aeroportuaria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AEROPUERTO")
public class Aeropuerto {

    @Id
    @NotNull
    private String idAeropuerto;

    private String nombre;

    private String estado;

    private String pais;
    /*
    private String id_ciudad;
     */

    @OneToOne(fetch = FetchType.LAZY) //Si se pone , cascade = CascadeType.ALL entonces cuando elimina un aeropuerto también elimina la cidad, se quita para que la ciudad no se borre y quede disponible para crear otro aeropuerto
    @JoinColumn(name = "id_ciudad")
    private Ciudad ciudad;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER) //Si no se pone esto el sistema muestra error (fail en el método do filter failed to lazily initialize a collection of role). Un usuario puede tener varios roles y un rol puede pertenecer a varios usuarios, para implementar lo anterior se crea una tabla intermedia que va a contener dos campos, uno va a ser el id del usuario y el otro el  id del rol para relacionar cada rol con cada usuario.
    @JoinTable(name = "AEROPUERTO_AEROLINEA", joinColumns = @JoinColumn(name = "ID_AEROPUERTO"), //usuario_rol es el nombre de la tabla, usuario_id va a ser la columna a que hace referencia la otra tabla hacia esta
            inverseJoinColumns = @JoinColumn(name = "ID_AEROLINEA"))
    private Set<Aerolinea> aerolineas = new HashSet<>(); //Conjunto de aerolineas

    //Se crea constructor lleno ya que este no debe llevar la colección de aerolíneas
    public Aeropuerto(@NotNull String idAeropuerto, String nombre, String estado, String pais, Ciudad ciudad) {
        this.idAeropuerto = idAeropuerto;
        this.nombre = nombre;
        this.estado = estado;
        this.pais = pais;
        this.ciudad = ciudad;
    }

}

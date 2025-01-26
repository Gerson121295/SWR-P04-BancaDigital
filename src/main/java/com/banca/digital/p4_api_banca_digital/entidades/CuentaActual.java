package com.banca.digital.p4_api_banca_digital.entidades;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
//No se agrega @DiscriminatorValue("CA") si en la clase CuentaBancaria se define @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS  o @Inheritance(strategy = InheritanceType.JOINED
@DiscriminatorValue("CA") //CA(Current Account) - Esta clase no se creará la tabla pero se encontrará los atributos definidos(sobregiro) en CuentaBancaria(es el padre) que tiene el @DiscriminatorColumn
public class CuentaActual extends CuentaBancaria{ //CuentaActual(clase hija) hereda los atributos del padre CuentaBancaria(tendra todos los campo del padre para hacer operaciones)

    private double sobregiro; //cuando el cliente gasta mas de lo que tiene

    //Getters and Setters
    public double getSobregiro() {
        return sobregiro;
    }

    public void setSobregiro(double sobregiro) {
        this.sobregiro = sobregiro;
    }
}

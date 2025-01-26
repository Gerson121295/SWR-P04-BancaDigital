package com.banca.digital.p4_api_banca_digital.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
//No se agrega @DiscriminatorValue("SA") si en la clase CuentaBancaria se define @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS o  @Inheritance(strategy = InheritanceType.JOINED
@DiscriminatorValue("SA") //SA(Saving Account) - Esta clase no se creará la tabla pero se encontrará los atributos definidos(tasaDeInteres) en CuentaBancaria(es el padre) que tiene el @DiscriminatorColumn
public class CuentaAhorro extends CuentaBancaria{ //CuentaAhorro(clase hija) hereda los atributos del padre CuentaBancaria(tendra todos los campo del padre para hacer operaciones)

    private double tasaDeInteres;

    //Getters and Setters
    public double getTasaDeInteres() {
        return tasaDeInteres;
    }

    public void setTasaDeInteres(double tasaDeInteres) {
        this.tasaDeInteres = tasaDeInteres;
    }
}

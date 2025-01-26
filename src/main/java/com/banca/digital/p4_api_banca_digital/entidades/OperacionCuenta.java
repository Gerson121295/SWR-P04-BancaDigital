package com.banca.digital.p4_api_banca_digital.entidades;

import com.banca.digital.p4_api_banca_digital.enums.TipoOperacion;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class OperacionCuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaOperacion;
    private double monto;
    private String descripcion;

    @Enumerated(EnumType.STRING) // indica a JPA cómo guardar este valor en la base de datos. STRING  los valores del enum se almacenan como cadenas de texto en la base de datos.
    private TipoOperacion tipoOperacion;

    //Relacion bidireccional muchas cuentas operaciones bancarias realiza una CuentaBancaria, y en la clase CuentaBancaria seria OneToMany 1 CuentaBancaria tiene muchas operaciones
    @ManyToOne
    //@JoinColumn(name = "cuenta_bancaria_id")    //Si no se usa @JoinColumn, Hibernate asignará un nombre predeterminado como cuenta_bancaria_id.
    private CuentaBancaria cuentaBancaria; //cuentaBancaria define la relacion con CuentaBancaria y OperacionCuenta y generará una FK cuenta_bancaria_id


    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }
}

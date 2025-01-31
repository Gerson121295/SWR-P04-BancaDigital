package com.banca.digital.p4_api_banca_digital.dtos;

import com.banca.digital.p4_api_banca_digital.enums.TipoOperacion;

import java.util.Date;

public class OperacionCuentaDTO {

    //Datos requeridos para este POJO
    private Long id;
    private Date fechaOperacion;
    private double monto;
    private TipoOperacion tipoOperacion;
    private String descripcion;


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

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

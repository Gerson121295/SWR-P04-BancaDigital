package com.banca.digital.p4_api_banca_digital.dtos;

public class DebitoDTO {

    private String cuentaId;
    private double monto;
    private String descripcion;

    //Getters And Setters
    public String getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
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


}

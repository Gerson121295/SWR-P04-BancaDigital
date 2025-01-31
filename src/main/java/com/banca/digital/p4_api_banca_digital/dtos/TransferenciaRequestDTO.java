package com.banca.digital.p4_api_banca_digital.dtos;

public class TransferenciaRequestDTO {

    private String cuentaPropietario;
    private String cuentaDestinatario;
    private double monto;
    private String descripcion;

    public String getCuentaPropietario() {
        return cuentaPropietario;
    }

    public void setCuentaPropietario(String cuentaPropietario) {
        this.cuentaPropietario = cuentaPropietario;
    }

    public String getCuentaDestinatario() {
        return cuentaDestinatario;
    }

    public void setCuentaDestinatario(String cuentaDestinatario) {
        this.cuentaDestinatario = cuentaDestinatario;
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

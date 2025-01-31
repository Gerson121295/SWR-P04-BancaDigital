package com.banca.digital.p4_api_banca_digital.dtos;

public class CuentaBancariaDTO {
    private String tipo; //define el tipo de la cuenta: CuentaActual o CuentaAhorro

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

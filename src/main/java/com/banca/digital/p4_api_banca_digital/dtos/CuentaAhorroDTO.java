package com.banca.digital.p4_api_banca_digital.dtos;

import com.banca.digital.p4_api_banca_digital.enums.EstadoCuenta;

import java.util.Date;

public class CuentaAhorroDTO extends CuentaBancariaDTO{ //CuentaAhorro(hija) hereda atributos y metodos del padre CuentaBancariaDTO

    //Se especifican los datos requeridos a mostrar
    private String id;
    private double balance;
    private Date FechaCreacion;
    private EstadoCuenta estadoCuenta;
    private ClienteDTO clienteDTO;
    private double tasaDeInteres;


    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public double getTasaDeInteres() {
        return tasaDeInteres;
    }

    public void setTasaDeInteres(double tasaDeInteres) {
        this.tasaDeInteres = tasaDeInteres;
    }
}

package com.banca.digital.p4_api_banca_digital.servicios;

import com.banca.digital.p4_api_banca_digital.entidades.Cliente;
import com.banca.digital.p4_api_banca_digital.entidades.CuentaActual;
import com.banca.digital.p4_api_banca_digital.entidades.CuentaAhorro;
import com.banca.digital.p4_api_banca_digital.entidades.CuentaBancaria;
import com.banca.digital.p4_api_banca_digital.excepciones.BalanceInsuficienteException;
import com.banca.digital.p4_api_banca_digital.excepciones.ClienteNotFoundException;
import com.banca.digital.p4_api_banca_digital.excepciones.CuentaBancariaNotFoundException;


import java.util.List;


public interface CuentaBancariaService {

    Cliente saveCliente(Cliente cliente);

    CuentaActual saveCuentaBancariaActual(Long clienteId, double balanceInicial, double sobregiro) throws ClienteNotFoundException;

    CuentaAhorro saveCuentaBancariaAhorro(Long clienteId, double balanceInicial, double tasaDeInteres)throws ClienteNotFoundException;

    List<Cliente> listClientes();

    CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;

    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;
    void transferir(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    List<CuentaBancaria> listCuentasBancarias();
}

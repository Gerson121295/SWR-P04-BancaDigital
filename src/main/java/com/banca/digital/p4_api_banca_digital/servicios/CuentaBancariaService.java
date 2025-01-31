package com.banca.digital.p4_api_banca_digital.servicios;

import com.banca.digital.p4_api_banca_digital.dtos.*;
import com.banca.digital.p4_api_banca_digital.entidades.Cliente;
import com.banca.digital.p4_api_banca_digital.entidades.CuentaBancaria;
import com.banca.digital.p4_api_banca_digital.excepciones.BalanceInsuficienteException;
import com.banca.digital.p4_api_banca_digital.excepciones.ClienteNotFoundException;
import com.banca.digital.p4_api_banca_digital.excepciones.CuentaBancariaNotFoundException;


import java.util.List;


public interface CuentaBancariaService {

    Cliente saveCliente(Cliente cliente);

    ClienteDTO saveClient(ClienteDTO clienteDTO);

    ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException;

    ClienteDTO updateCliente(ClienteDTO clienteDTO);

    void deleteCliente(Long clienteId) throws ClienteNotFoundException;

    List<ClienteDTO> searchClientes(String keyword);

    CuentaActualDTO saveCuentaBancariaActual(Long clienteId, double balanceInicial, double sobregiro) throws ClienteNotFoundException;
    CuentaAhorroDTO saveCuentaBancariaAhorro(Long clienteId, double balanceInicial, double tasaDeInteres)throws ClienteNotFoundException;


    List<ClienteDTO> listClientes();  //Sin DTO Retorna Cliente: List<Cliente>

    CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;

    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;
    void transferir(String cuentaIdPropietario, String cuentaIdDestinatario, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;
    List<CuentaBancariaDTO> listCuentasBancarias();

    List<OperacionCuentaDTO> listHistorialDeLaCuenta(String cuentaId);

    HistorialCuentaDTO getHistorialCuenta(String cuentaId, int page, int size) throws CuentaBancariaNotFoundException;

}

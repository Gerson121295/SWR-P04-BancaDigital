package com.banca.digital.p4_api_banca_digital.servicios.impl;

import com.banca.digital.p4_api_banca_digital.entidades.*;
import com.banca.digital.p4_api_banca_digital.enums.TipoOperacion;
import com.banca.digital.p4_api_banca_digital.excepciones.BalanceInsuficienteException;
import com.banca.digital.p4_api_banca_digital.excepciones.ClienteNotFoundException;
import com.banca.digital.p4_api_banca_digital.excepciones.CuentaBancariaNotFoundException;
import com.banca.digital.p4_api_banca_digital.repositorios.ClienteRepository;
import com.banca.digital.p4_api_banca_digital.repositorios.CuentaBancariaRepository;
import com.banca.digital.p4_api_banca_digital.repositorios.OperacionCuentaRepository;
import com.banca.digital.p4_api_banca_digital.servicios.CuentaBancariaService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j //habilita el uso del registro de logs (logging) mediante la biblioteca SLF4J. Se crea una instancia de un logger log que puede ser utilizada para registrar mensajes en diferentes niveles (info, debug, error, etc.).
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;


    @Override
    public Cliente saveCliente(Cliente cliente) {
        //log.info("Guardando un nuevo Cliente");
        Cliente clienteDB = clienteRepository.save(cliente);
        return clienteDB;
    }

    @Override
    public CuentaActual saveCuentaBancariaActual(Long clienteId, double balanceInicial, double sobregiro) throws ClienteNotFoundException {
        //Busca el cliente por su id que se recibe
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null); //orElse sig. si el cliente no se encuentra se pasa un null

        if(cliente == null){
            throw new ClienteNotFoundException("Cliente no encontrado");
        }
        //si cliente no es null - existe el cliente
        CuentaActual cuentaActual = new CuentaActual(); //crea una nueva instancia de CuentaActual. o Crea un nuevo objeto que representa a CuentaActual
        cuentaActual.setId(UUID.randomUUID().toString()); // Genera un ID único para la cuenta actual.
        cuentaActual.setFechaCreacion(new Date()); // Establece la fecha de creación de la cuenta.
        cuentaActual.setBalance(balanceInicial); // Asigna el balance inicial.
        cuentaActual.setSobregiro(sobregiro); // Asigna el monto del sobregiro.
        cuentaActual.setCliente(cliente); // Asocia la cuenta actual al cliente.

        //Se crea un nuevo objeto llamado CuentaActualDB en el cual guarda cuentaActual. // Guarda la cuenta actual en la DB y retorna la instancia persistida.
        CuentaActual cuentaActualDB = cuentaBancariaRepository.save(cuentaActual);
        return cuentaActualDB;
    }

    @Override
    public CuentaAhorro saveCuentaBancariaAhorro(Long clienteId, double balanceInicial, double tasaDeInteres) throws ClienteNotFoundException {
        //Busca el cliente por su id que se recibe
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null); //orElse sig. si el cliente no se encuentra se pasa un null

        if(cliente == null){
            throw new ClienteNotFoundException("Cliente no encontrado");
        }
        //si cliente no es null - existe el cliente
            CuentaAhorro cuentaAhorro = new CuentaAhorro(); //crea una nueva instancia de CuentaActual. o Crea un nuevo objeto que representa a CuentaActual
            cuentaAhorro.setId(UUID.randomUUID().toString()); // Genera un ID único para la cuenta actual.
            cuentaAhorro.setFechaCreacion(new Date()); // Establece la fecha de creación de la cuenta.
            cuentaAhorro.setBalance(balanceInicial); // Asigna el balance inicial.
            cuentaAhorro.setTasaDeInteres(tasaDeInteres); // Asigna el monto del sobregiro.
            cuentaAhorro.setCliente(cliente); // Asocia la cuenta actual al cliente.

        //Se crea un nuevo objeto llamado CuentaActualDB en el cual guarda cuentaActual. // Guarda la cuenta actual en la DB y retorna la instancia persistida.
        CuentaAhorro cuentaAhorroDB = cuentaBancariaRepository.save(cuentaAhorro);
        return cuentaAhorroDB;
    }

    @Override
    public List<Cliente> listClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId) //busca la cuenta bancaria por id a listar
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontras")); //si no encuentra la cuenta bancaria muestra la exeption
        return cuentaBancaria;
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = getCuentaBancaria(cuentaId); //busca la cuenta bancaria por id a debitar

        //validación asegura que no se pueda debitar un monto mayor al balance disponible en la cuenta bancaria
        if(cuentaBancaria.getBalance() < monto){ // Verifica que el monto a retirar no sea mayor que el balance disponible.
            throw new BalanceInsuficienteException("Balance insuficiente");
        }

        //Se realiza la operacion de debitar o retirar dinero a una cuenta
        OperacionCuenta operacionCuenta = new OperacionCuenta(); //Crea una nueva instacia de OperacionCuenta llamado operacionCuenta para asignar los nuevos datos
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);

        operacionCuentaRepository.save(operacionCuenta); //guarda la operacionCuenta en la DB

        //Actualizacion de la cuentaBancaria - a la cuentaBancaria se le asigna el nuevo balance despues de la operacion
        cuentaBancaria.setBalance(cuentaBancaria.getBalance() - monto); //se resta el monto a retirar
        cuentaBancariaRepository.save(cuentaBancaria); //guarda el nuevo balance a la cuentaBancaria luedo de debitar(retirar dinero)
    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = getCuentaBancaria(cuentaId); //busca la cuenta bancaria por id a crebitar

        //Se realiza la operacion de creditar a una cuenta
        OperacionCuenta operacionCuenta = new OperacionCuenta(); //Crea una nueva instacia de OperacionCuenta llamado operacionCuenta para asignar los nuevos datos
        operacionCuenta.setTipoOperacion(TipoOperacion.CREDITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);

        operacionCuentaRepository.save(operacionCuenta); //guarda la operacionCuenta en la DB

        //Actualizacion de la cuentaBancaria - a la cuentaBancaria se le asigna el nuevo balance despues de la operacion
        cuentaBancaria.setBalance(cuentaBancaria.getBalance() + monto); //se sumo el nuevo credito
        cuentaBancariaRepository.save(cuentaBancaria); //guarda el nuevo balance a la cuentaBancaria luedo de creditar(depositar dinero)

    }

    @Override
    public void transferir(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        debit(cuentaIdPropietario, monto, "Transferencia a: " + cuentaIdDestinatario); //retirar dinero de la cuenta
        credit(cuentaIdDestinatario, monto, "Transferencia de: " + cuentaIdPropietario);//depositar dinero a la cuenta destino
    }

    @Override
    public List<CuentaBancaria> listCuentasBancarias() {
        return cuentaBancariaRepository.findAll();
    }
}

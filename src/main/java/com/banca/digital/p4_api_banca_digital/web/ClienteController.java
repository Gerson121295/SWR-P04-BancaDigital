package com.banca.digital.p4_api_banca_digital.web;

import com.banca.digital.p4_api_banca_digital.entidades.Cliente;
import com.banca.digital.p4_api_banca_digital.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    //Listar clientes: GET: http://localhost:8080/api/v1/clientes
    @GetMapping("/clientes")
    public List<Cliente> listarCliente(){
        return cuentaBancariaService.listClientes();
    }




}

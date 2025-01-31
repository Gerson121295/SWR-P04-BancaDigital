package com.banca.digital.p4_api_banca_digital.web;


import com.banca.digital.p4_api_banca_digital.dtos.*;
import com.banca.digital.p4_api_banca_digital.excepciones.BalanceInsuficienteException;
import com.banca.digital.p4_api_banca_digital.excepciones.CuentaBancariaNotFoundException;
import com.banca.digital.p4_api_banca_digital.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    //Listar Cuentas: GET - http://localhost:8080/api/v1/cuentas
    @GetMapping("cuentas/{cuentaId}")
    public CuentaBancariaDTO listarDatosDeCuentaBancaria(@PathVariable String cuentaId) throws CuentaBancariaNotFoundException{
        return cuentaBancariaService.getCuentaBancaria(cuentaId);
    }

    //Listar Cuentas por id: GET - http://localhost:8080/api/v1/cuentas/32f1ddae-3993-4eeb-81db-d9b5d4a6d570
    @GetMapping("/cuentas")
    public List<CuentaBancariaDTO> listarCuentasBancarias(){
        return cuentaBancariaService.listCuentasBancarias();
    }

    //Listar el historial de opraciones que se han realizado en una cuenta: GET:  http://localhost:8080/api/v1/cuentas/32f1ddae-3993-4eeb-81db-d9b5d4a6d570/operaciones
    @GetMapping("/cuentas/{cuentaId}/operaciones")
    public List<OperacionCuentaDTO> listHistorialDeCuentas(@PathVariable String cuentaId){
        return cuentaBancariaService.listHistorialDeLaCuenta(cuentaId);
    }

    //Listar el historial con Paginacion de opraciones que se han realizado en una cuenta: GET:  http://localhost:8080/api/v1/cuentas/32f1ddae-3993-4eeb-81db-d9b5d4a6d570/pageOperaciones     Pasarle parametros en la url: ?page=3&size=5    o escribirlo en key(page, size). value(3,5)
    @GetMapping("/cuentas/{cuentaId}/pageOperaciones")
    public HistorialCuentaDTO listHistorialDeLaCuentaPaginado(@PathVariable String cuentaId, @RequestParam(name="page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "3") int size) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getHistorialCuenta(cuentaId, page, size);
    }

    //Retirar de la cuenta: POST:  http://localhost:8080/api/v1/cuentas/debito     Peticion: {"cuentaId":"acbca23d-bf64-4eb1-a863-5a9afd4e34d4", "monto":7985, "descripcion":"donacion"}
    @PostMapping("/cuentas/debito")
    public DebitoDTO realizarDebito(@RequestBody DebitoDTO debitoDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.debit(debitoDTO.getCuentaId(), debitoDTO.getMonto(), debitoDTO.getDescripcion());
        return debitoDTO;
    }

    //Depositar a la cuenta: POST: http://localhost:8080/api/v1/cuentas/credito   Peticion: {"cuentaId":"acbca23d-bf64-4eb1-a863-5a9afd4e34d4", "monto":50000, "descripcion":"Deposito venta de casa"}
    @PostMapping("/cuentas/credito")
    public CreditoDTO realizarCredito(@RequestBody CreditoDTO creditoDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.credit(creditoDTO.getCuentaId(), creditoDTO.getMonto(), creditoDTO.getDescripcion());
        return creditoDTO;
    }

    //Transferencia de una cuenta a otra: POST:  http://localhost:8080/api/v1/cuentas/transferencia   {"cuentaPropietario":"acbca23d-bf64-4eb1-a863-5a9afd4e34d4", "cuentaDestinatario": "95fe7abc-62b6-450e-a437-7ce7cb17005e","monto":10000,"descripcion":"Pago por servicios"}
    @PostMapping("/cuentas/transferencia")
    public void realizarTransferencia(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.transferir(transferenciaRequestDTO.getCuentaPropietario(), transferenciaRequestDTO.getCuentaDestinatario(), transferenciaRequestDTO.getMonto(), transferenciaRequestDTO.getDescripcion());

    }

}

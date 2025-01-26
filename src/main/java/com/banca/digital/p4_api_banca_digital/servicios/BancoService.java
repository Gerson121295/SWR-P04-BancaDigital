package com.banca.digital.p4_api_banca_digital.servicios;

import com.banca.digital.p4_api_banca_digital.entidades.CuentaActual;
import com.banca.digital.p4_api_banca_digital.entidades.CuentaAhorro;
import com.banca.digital.p4_api_banca_digital.entidades.CuentaBancaria;
import com.banca.digital.p4_api_banca_digital.repositorios.CuentaBancariaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class BancoService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    //Servicio de prueba que se manda a ejecutar desde el main
    public void consultar(){
        CuentaBancaria cuentaBancariaDB = cuentaBancariaRepository.findById("1e79f562-217a-4d22-a6f5-82f482509c55").orElse(null); //otra forma agregar: .get();

        if(cuentaBancariaDB != null){
            System.out.println("************************");
            System.out.println("ID :"+cuentaBancariaDB.getId());
            System.out.println("Balance de la cuenta:"+cuentaBancariaDB.getBalance());
            System.out.println("Estado:"+cuentaBancariaDB.getEstadoCuenta());
            System.out.println("Fecha de creacion:"+cuentaBancariaDB.getFechaCreacion());
            System.out.println("Cliente"+cuentaBancariaDB.getCliente().getNombre());
            System.out.println("Nombre de la clase:"+cuentaBancariaDB.getClass().getSimpleName());

            if(cuentaBancariaDB instanceof CuentaActual){ //si la cuentaBancaria es una instancia de CuentaActual se le agrega el sobregiro
                System.out.println("Sobregiro:"+((CuentaActual) cuentaBancariaDB).getSobregiro());
            }else if(cuentaBancariaDB instanceof CuentaAhorro){ //si la cuentaBancaria es una instancia de CuentaAhorro se le agrega la tasaDeInteres
                System.out.println("Tasa de interes:"+((CuentaAhorro) cuentaBancariaDB).getTasaDeInteres());
            }

            cuentaBancariaDB.getOperacionesCuenta().forEach(operacionCuenta -> {
                System.out.println("------------------------------------");
                System.out.println("Tipo de Operacion:"+operacionCuenta.getTipoOperacion());
                System.out.println("Fecha de Operacion:"+operacionCuenta.getFechaOperacion());
                System.out.println("Monto"+operacionCuenta.getMonto());
            });
        }
    }
}

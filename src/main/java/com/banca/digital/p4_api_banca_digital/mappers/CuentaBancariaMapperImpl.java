package com.banca.digital.p4_api_banca_digital.mappers;

import com.banca.digital.p4_api_banca_digital.dtos.ClienteDTO;
import com.banca.digital.p4_api_banca_digital.dtos.CuentaActualDTO;
import com.banca.digital.p4_api_banca_digital.dtos.CuentaAhorroDTO;
import com.banca.digital.p4_api_banca_digital.dtos.OperacionCuentaDTO;
import com.banca.digital.p4_api_banca_digital.entidades.Cliente;
import com.banca.digital.p4_api_banca_digital.entidades.CuentaActual;
import com.banca.digital.p4_api_banca_digital.entidades.CuentaAhorro;
import com.banca.digital.p4_api_banca_digital.entidades.OperacionCuenta;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CuentaBancariaMapperImpl {

            /*      MAPEO DE Cliente a ClienteDTO    */
    //una Entidad Cliente se mapea(convierte) a DTO
    public ClienteDTO mapearDeCliente(Cliente cliente){ //mapearDeCliente de tipo ClienteDTO recibe cliente de tipo Cliente

        ClienteDTO clienteDTO = new ClienteDTO(); //crea una instancia de ClienteDTO
        BeanUtils.copyProperties(cliente, clienteDTO); //se copia las propiedades de cliente a clienteDTO
        return clienteDTO;
    }

    //El ClienteDTO se mapea (convierte) a Entidad Cliente
    public Cliente mapearDeClienteDTO(ClienteDTO clienteDTO){ //mapearDeCliente de tipo Cliente recibe clienteDTO de tipo ClienteDTO

        Cliente cliente = new Cliente(); //crea una instancia de Cliente
        BeanUtils.copyProperties(clienteDTO, cliente); //se copia las propiedades de clienteDTO a cliente
        return cliente;
    }


            /*      MAPEO DE CuentaAhorro y CuentaAhorroDTO    */
    //Mapear(convertir) una entidad(CuentaAhorro) a DTO(CuentaAhorroDTO)
    public CuentaAhorroDTO mapearDeCuentaAhorro(CuentaAhorro cuentaAhorro){ //recibe cuentaAhorro a mapear
        CuentaAhorroDTO cuentaAhorroDTO = new CuentaAhorroDTO(); //crea una instancia de CuentaAhorroDTO
        BeanUtils.copyProperties(cuentaAhorro, cuentaAhorroDTO); //se copia las propiedades de cuentaAhorro a cuentaAhorroDTO

        cuentaAhorroDTO.setClienteDTO(mapearDeCliente(cuentaAhorro.getCliente())); //se establece el cliente de cuentaAhorro al cliente de cuentaAhorroDTO mediante set
        cuentaAhorroDTO.setTipo(cuentaAhorro.getClass().getSimpleName()); //Agrega el tipo de la cuenta: CuentaActual o CuentaAhorro

        return cuentaAhorroDTO; //Retorna el objeto cuentaAhorroDTO
    }

    //Mapear(convertir) un DTO(CuentaAhorroDTO) a una entidad(CuentaAhorro)
    public CuentaAhorro mapearDeCuentaAhorroDTO(CuentaAhorroDTO cuentaAhorroDTO){ //recibe cuentaAhorroDTO a mapear
        CuentaAhorro cuentaAhorro = new CuentaAhorro(); //crea una instancia de CuentaAhorro
        BeanUtils.copyProperties(cuentaAhorroDTO, cuentaAhorro); //se copia las propiedades de cuentaAhorroDTO a cuentaAhorro

        cuentaAhorro.setCliente(mapearDeClienteDTO(cuentaAhorroDTO.getClienteDTO())); //se establece el cliente de cuentaAhorroDTO al cliente de cuentaAhorro mediante set
        return cuentaAhorro; //Retorna el objeto cuentaAhorro
    }



    /*      MAPEO DE CuentaActual y CuentaActualDTO    */
    //Mapear(convertir) una entidad(CuentaActual) a DTO(CuentaActualDTO)
    public CuentaActualDTO mapearDeCuentaActual(CuentaActual cuentaActual){ //recibe cuentaActual a mapear
        CuentaActualDTO cuentaActualDTO = new CuentaActualDTO(); //crea una instancia de CuentaActualDTO
        BeanUtils.copyProperties(cuentaActual, cuentaActualDTO); //se copia las propiedades de cuentaActual a cuentaActualDTO

        cuentaActualDTO.setClienteDTO(mapearDeCliente(cuentaActual.getCliente())); //se establece el cliente de cuentaActual al cliente de cuentaActualDTO mediante set
        cuentaActualDTO.setTipo(cuentaActual.getClass().getSimpleName()); //Agrega el tipo de la cuenta: CuentaActual o CuentaAhorro

        return cuentaActualDTO; //Retorna el objeto cuentaActualDTO
    }

    //Mapear(convertir) un DTO(CuentaActualDTO) a una entidad(CuentaActual)
    public CuentaActual mapearDeCuentaActualDTO(CuentaActualDTO cuentaActualDTO){ //recibe cuentaActualDTO a mapear
        CuentaActual cuentaActual = new CuentaActual(); //crea una instancia de CuentaActual
        BeanUtils.copyProperties(cuentaActualDTO, cuentaActual); //se copia las propiedades de cuentaActualDTO a cuentaActual

        cuentaActual.setCliente(mapearDeClienteDTO(cuentaActualDTO.getClienteDTO())); //se establece el cliente de cuentaActualDTO al cliente de cuentaActual mediante set
        return cuentaActual; //Retorna el objeto cuentaActual
    }


    /*     MAPEO DE OperacionCuenta a OperacionCuentaDTO     */
    //Mapear(convertir) una entidad(OperacionCuenta) a DTO(OperacionCuentaDTO)
    public OperacionCuentaDTO mapearDeOperacionCuenta(OperacionCuenta operacionCuenta){ //recibe la operacionCuenta a convertir a DTO
        OperacionCuentaDTO operacionCuentaDTO = new OperacionCuentaDTO();
        BeanUtils.copyProperties(operacionCuenta, operacionCuentaDTO); //copia los atributos de operacionCuenta(entidad) a operacionCuentaDTO(dto)

        return operacionCuentaDTO;
    }




}

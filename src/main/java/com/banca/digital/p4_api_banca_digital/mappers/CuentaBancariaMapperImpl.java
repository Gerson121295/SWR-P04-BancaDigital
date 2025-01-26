package com.banca.digital.p4_api_banca_digital.mappers;

import com.banca.digital.p4_api_banca_digital.dtos.ClienteDTO;
import com.banca.digital.p4_api_banca_digital.entidades.Cliente;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CuentaBancariaMapperImpl {

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

}

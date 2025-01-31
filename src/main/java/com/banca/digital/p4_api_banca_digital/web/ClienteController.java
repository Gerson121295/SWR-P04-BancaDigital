package com.banca.digital.p4_api_banca_digital.web;

import com.banca.digital.p4_api_banca_digital.dtos.ClienteDTO;
import com.banca.digital.p4_api_banca_digital.excepciones.ClienteNotFoundException;
import com.banca.digital.p4_api_banca_digital.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    //Listar clientes: GET: http://localhost:8080/api/v1/clientes
    @GetMapping("/clientes")
    public List<ClienteDTO> listarCliente(){
        return cuentaBancariaService.listClientes();
    }

    //Buscar cliente por id.   GET: http://localhost:8080/api/v1/clientes/2
    @GetMapping("/clientes/{id}")
    public ClienteDTO listarDatosDelCliente(@PathVariable(name="id") Long clienteId) throws ClienteNotFoundException{
        return cuentaBancariaService.getCliente(clienteId);
    }

    //Crear cliente:  POST: http://localhost:8080/api/v1/clientes   {"nombre": "Gema","email": "gema@gmail.com" }
    @PostMapping("/clientes")
    public ClienteDTO guardarCliente(@RequestBody ClienteDTO clienteDTO){
        return cuentaBancariaService.saveClient(clienteDTO);
    }

    //Actualizar cliente:  PUT: http://localhost:8080/api/v1/clientes/5   {"nombre": "Gema","email": "gema@gmail.com" }
    @PutMapping("/clientes/{clienteId}")
    public ClienteDTO actualizarCliente(@PathVariable Long clienteId, @RequestBody ClienteDTO clienteDTO){
        clienteDTO.setId(clienteId); //establece el id que recibe de parametro para que al guardar tenga el mismo id de antes
        return cuentaBancariaService.updateCliente(clienteDTO);
    }

    //Eliminar cliente por id.   DELETE: http://localhost:8080/api/v1/clientes/2
    @DeleteMapping("/clientes/{clienteId}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long clienteId){ //public void eliminarCliente
        //cuentaBancariaService.deleteCliente(clienteId);
        try {
            cuentaBancariaService.deleteCliente(clienteId);
            return ResponseEntity.ok("Cliente con ID " + clienteId + " eliminado correctamente.");
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //Buscar clientes por keyword
    @GetMapping("/clientes/search")  //GET   http://localhost:8080/api/v1/clientes/search   ?keyword=Biag   en QueryParams Agregar: key:keyword, value:Biag   aparecera asi:   http://localhost:8080/api/v1/clientes/search?keyword=Biag
    public List<ClienteDTO> buscarClientes(@RequestParam(name="keyword", defaultValue = "") String keyword){ //defaultValue="" vacio porque devolvera todo
        return cuentaBancariaService.searchClientes("%"+keyword+"%"); //los "%" significa que la keyword se buscara conicidencia o comparará por derecha a izquierda a la palabra keyword con nombre
    }

}


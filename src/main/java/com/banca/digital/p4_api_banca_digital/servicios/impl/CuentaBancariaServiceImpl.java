package com.banca.digital.p4_api_banca_digital.servicios.impl;

import com.banca.digital.p4_api_banca_digital.dtos.*;
import com.banca.digital.p4_api_banca_digital.entidades.*;
import com.banca.digital.p4_api_banca_digital.enums.TipoOperacion;
import com.banca.digital.p4_api_banca_digital.excepciones.BalanceInsuficienteException;
import com.banca.digital.p4_api_banca_digital.excepciones.ClienteNotFoundException;
import com.banca.digital.p4_api_banca_digital.excepciones.CuentaBancariaNotFoundException;
import com.banca.digital.p4_api_banca_digital.mappers.CuentaBancariaMapperImpl;
import com.banca.digital.p4_api_banca_digital.repositorios.ClienteRepository;
import com.banca.digital.p4_api_banca_digital.repositorios.CuentaBancariaRepository;
import com.banca.digital.p4_api_banca_digital.repositorios.OperacionCuentaRepository;
import com.banca.digital.p4_api_banca_digital.servicios.CuentaBancariaService;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Transactional
//@Slf4j   //habilita el uso del registro de logs (logging) mediante la biblioteca SLF4J. Se crea una instancia de un logger log que puede ser utilizada para registrar mensajes en diferentes niveles (info, debug, error, etc.).
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    private static final Logger log = LoggerFactory.getLogger(CuentaBancariaServiceImpl.class); //habilita el uso de log - la anotacion @Slf4j  no me funciona debido a que esta importada de lombok

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;


    //Agregar implementacion de DTO se agrega la inyeccion
    @Autowired
    private CuentaBancariaMapperImpl cuentaBancariaMapper;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        //log.info("Guardando un nuevo Cliente");
        Cliente clienteDB = clienteRepository.save(cliente);
        return clienteDB;
    }

    // USO DE DTOs:
// En una solicitud POST PUT/PATCH: se recibe un DTO desde el cliente, se procesa y convierte en una entidad para guardarla en la BD.
// En una solicitud GET: se obtiene la entidad desde la BD, se convierte en un DTO y se devuelve para exponerlo al cliente.

    //TRABAJAR DTO: Cuando es un POST se le envia un DTO y se procesa y convierte a una entidad para guardarlo a la BD
    @Override
    public ClienteDTO saveClient(ClienteDTO clienteDTO){
        log.info("Guardando un nuevo Cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO); //se recibe un clienteDTo se convierte o mapea a una entidad Tipo Cliente
        Cliente clienteDB = clienteRepository.save(cliente); //se guarda el cliente en la BD
        return cuentaBancariaMapper.mapearDeCliente(clienteDB); //convierte el clienteDB de tipo Cliente a ClienteDTO y Retorna el clienteDB guardado en la BD pero en DTO
    }

    //TRABAJAR DTO: Cuando es un GET se tiene la entidad y se convierte a DTO y mostrarlo en publico
    @Override
    public ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId) //busca un cliente por id
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado")); //Si el cliente no existe, lanza una excepción ClienteNotFoundException con el mensaje "Cliente no encontrado".
        return cuentaBancariaMapper.mapearDeCliente(cliente); // Convierte el objeto Cliente en un objeto ClienteDTO. Este proceso se llama mapeo.
    }

    @Override
    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        log.info("Actualizando cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO); //convierte el clienteDTO recibido a entidad de tipo Cliente
        Cliente clienteDB = clienteRepository.save(cliente); //guarda el cliente en la BD y lo guarda en clienteDB
        return cuentaBancariaMapper.mapearDeCliente(clienteDB); //convierte entidad clienteDB a DTO y lo retorna
    }

    @Override
    public void deleteCliente(Long clienteId) throws ClienteNotFoundException {
        //clienteRepository.deleteById(clienteId);
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con ID " + clienteId + " no encontrado."));

        //Eliminar o desvincular las cuentas asociadas o todas las relaciones de Cliente antes de ser eliminada de lo contrario no se permitirá
        //Nota: si configuras la relación  @OneToMany con cascade = CascadeType.REMOVE en la clase Cliente y CuentaBancaria, ya no es necesario agregar el código manual para desvincular o eliminar las cuentas bancarias
        //cliente.getCuentasBancarias().forEach(cuenta -> cuentaBancariaRepository.delete(cuenta));

        // Finalmente, eliminar el cliente
        clienteRepository.delete(cliente);
    }

    @Override
    public List<ClienteDTO> searchClientes(String keyword) {

        //obtener y guardar en la lista clientes a los clientes donde su nombre sea como el keyword enviado
        List<Cliente> clientes = clienteRepository.searchClientes(keyword);

        //convertir la lista clientes de entidad a DTO esto para enviar solo data necesaria a mostrar
        List<ClienteDTO> clientesDTOS = clientes.stream().map(
                cliente -> cuentaBancariaMapper.mapearDeCliente(cliente)).collect(Collectors.toList());
        return clientesDTOS;
    }

    @Override
    public CuentaActualDTO saveCuentaBancariaActual(Long clienteId, double balanceInicial, double sobregiro) throws ClienteNotFoundException { //public CuentaActual
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
        return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActualDB);  //return cuentaActualDB; //se convierte de entidad a DTO luego se retorna
    }



    @Override
    public CuentaAhorroDTO saveCuentaBancariaAhorro(Long clienteId, double balanceInicial, double tasaDeInteres) throws ClienteNotFoundException { //public CuentaAhorro
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
        return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorroDB); //return cuentaAhorroDB;  //se convierte de entidad a DTO luego se retorna
    }

    @Override
    public List<ClienteDTO> listClientes() {
        List<Cliente> clientes = clienteRepository.findAll(); // Se obtiene una lista de objetos de tipo Cliente desde el repositorio mediante el método findAll()

        //Transforma una lista de objetos Cliente en una lista de objetos ClienteDTO
        List<ClienteDTO> clienteDTOS = clientes.stream() // Stream es una secuencia de elementos que permite realizar operaciones como mapear, filtrar
                .map(cliente -> cuentaBancariaMapper.mapearDeCliente(cliente))  // Transforma cada objeto Cliente en un objeto ClienteDTO utilizando el método mapearDeCliente del mapeador cuentaBancariaMapper
                .collect(Collectors.toList()); //Convierte el Stream resultante (de ClienteDTO) en una lista.
        return clienteDTOS;
    }


    @Override
    public CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId) //busca la cuenta bancaria por id a listar
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontras")); //si no encuentra la cuenta bancaria muestra la exeption

        if (cuentaBancaria instanceof CuentaAhorro) { //si cuentaBancaria es instancia de CuentaAhorro
            CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria; //se castea cuentaBancaria a CuentaAhorro
            return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro); //convierte entidad(recibe cuentaAhorro) a DTO
        }
        //Si la cuentaBancaria no sea de cuentaAhorro entonces será cuenta Actual
        else {
            CuentaActual cuentaActual = (CuentaActual) cuentaBancaria; //se castea cuentaBancaria a CuentaActual
            return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual); //convierte entidad(recibe cuentaActual) a DTO
        }
    }


    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId) //busca la cuenta bancaria por id a listar
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontras")); //si no encuentra la cuenta bancaria muestra la exeption

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

        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId) //busca la cuenta bancaria por id a listar
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontras")); //si no encuentra la cuenta bancaria muestra la exeption

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
    public void transferir(String cuentaIdPropietario, String cuentaIdDestinatario, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        debit(cuentaIdPropietario, monto, "Transferencia a: " + cuentaIdDestinatario); //retirar dinero de la cuenta
        credit(cuentaIdDestinatario, monto, "Transferencia de: " + cuentaIdPropietario);//depositar dinero a la cuenta destino
    }

    @Override
    public List<CuentaBancariaDTO> listCuentasBancarias() {
        List<CuentaBancaria> cuentasBancarias = cuentaBancariaRepository.findAll(); //obtiene la lista(entidades) de cuentasBancarias

        //Lista de DTOS para recorrer la lista cuentasBancarias, y por cada cuentaBancaria validar si es instancia de CuentaAhorro o Actual esto para mapear cada cuentaBancaria a su DTO
        List<CuentaBancariaDTO> cuentasBancariasDTOS = cuentasBancarias.stream() //stream procesa y manipula colecciones de datos de forma eficiente. Se puede utilizar para filtrar, mapear, reducir y ordenar
                .map(cuentaBancaria -> { //con stream se procesa la lista cuentasBancarias y con .map se recorre por cada cuentaBancaria el resultado lo guarda en cuentaBancariaDTOS

                    //valida si cuentaBancaria es CuentaAhorro o CuentaActual
                    if (cuentaBancaria instanceof CuentaAhorro) {
                        CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria; //se castea cuentaBancaria a CuentaAhorro y se guarda en cuentaAhorro de tipo CuentaAhorro
                        return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro); //convierte entidad(cuentaAhorro) a un DTO(CuentaAhorroDTO)
                    }
                    //Si la cuentaBancaria no sea de cuentaAhorro entonces será cuenta Actual
                    else {
                        CuentaActual cuentaActual = (CuentaActual) cuentaBancaria; //se castea cuentaBancaria a CuentaActual
                        return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual); //convierte entidad(recibe cuentaActual) a DTO
                    }
        }).collect(Collectors.toList());
        return cuentasBancariasDTOS;
    }

    @Override
    public List<OperacionCuentaDTO> listHistorialDeLaCuenta(String cuentaId) {
        //obtiene en una lista operacionesDeCuenta las operaciones que se han realziado en una cuenta
        List<OperacionCuenta> operacionesDeCuenta = operacionCuentaRepository.findByCuentaBancariaId(cuentaId);

        return operacionesDeCuenta.stream().map(operacionCuenta ->
                cuentaBancariaMapper.mapearDeOperacionCuenta(operacionCuenta) //convierte cada operacionCuenta(entidad) realizada en la cuenta en DTO
        ).collect(Collectors.toList());
    }

    @Override
    public HistorialCuentaDTO getHistorialCuenta(String cuentaId, int page, int size) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId) //busca la cuenta bancaria por id a listar
                //.orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontras")); //si no encuentra la cuenta bancaria muestra la exeption
                .orElse(null); //forma 2 de validar y luego colocar el if para lanzar la excepcion si es null
        if(cuentaBancaria == null){
            throw new CuentaBancariaNotFoundException("Cuenta no encontrada");
        }
        //OperacionCuenta guarda la informacion devuelta en paginas en formato de Entidad
        //Page<OperacionCuenta> operacionesCuenta = operacionCuentaRepository.findByCuentaBancariaId(cuentaId, PageRequest.of(page, size)); //Cuenta bancaria por ID
        Page<OperacionCuenta> operacionesCuenta = operacionCuentaRepository.findByCuentaBancariaIdOrderByFechaOperacionDesc(cuentaId, PageRequest.of(page, size)); //lista cuenta bancaria por fecha de operacion

        //Proceso de conversion de la lista operacionCuenta de la entidad a DTO
        HistorialCuentaDTO historialCuentaDTO = new HistorialCuentaDTO(); //crea una instancia de HistorialCuentaDTO
        List<OperacionCuentaDTO> operacionesCuentaDTOS = operacionesCuenta.getContent().stream().map( //con stream y map recorre la lista operacionesCuenta
                operacionCuenta -> cuentaBancariaMapper.mapearDeOperacionCuenta(operacionCuenta)).collect(Collectors.toList()); //cada operacionCuenta de la lista se convierte a DTO
            historialCuentaDTO.setOperacionesCuentaDTOS(operacionesCuentaDTOS);
            historialCuentaDTO.setCuentaId(cuentaBancaria.getId());
            historialCuentaDTO.setBalance(cuentaBancaria.getBalance());
            historialCuentaDTO.setCurrentPage(page);
            historialCuentaDTO.setPapeSize(size);
            historialCuentaDTO.setTotalPage(operacionesCuenta.getTotalPages());
            return historialCuentaDTO;
    }
}


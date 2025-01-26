package com.banca.digital.p4_api_banca_digital;

import com.banca.digital.p4_api_banca_digital.entidades.*;
import com.banca.digital.p4_api_banca_digital.enums.EstadoCuenta;
import com.banca.digital.p4_api_banca_digital.enums.TipoOperacion;
import com.banca.digital.p4_api_banca_digital.repositorios.ClienteRepository;
import com.banca.digital.p4_api_banca_digital.repositorios.CuentaBancariaRepository;
import com.banca.digital.p4_api_banca_digital.repositorios.OperacionCuentaRepository;
import com.banca.digital.p4_api_banca_digital.servicios.BancoService;
import com.banca.digital.p4_api_banca_digital.servicios.CuentaBancariaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class P4ApiBancaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(P4ApiBancaDigitalApplication.class, args);
	}



	//@Bean
		//al comentar Bean no se ejecutará este codigo - Prueba al CuentaBancariaService
	CommandLineRunner start(CuentaBancariaService cuentaBancariaService) {
		return args -> {
			Stream.of("Christian", "Julia", "Biaggio", "Lanudo").forEach(nombre -> {
				Cliente cliente = new Cliente(); // Crea una nueva instancia de la clase Cliente // Crea un nuevo objeto Cliente para representar a un cliente
				//Establece datos de los clientes
				cliente.setNombre(nombre);
				cliente.setEmail(nombre + "@gmail.com");
				cuentaBancariaService.saveCliente(cliente);

			});

			cuentaBancariaService.listClientes().forEach(cliente -> { //recorre la lista de cliente por cada cliente
				try {
					//crea una nueva cuenta actual a la cual se le agregan los datos
					cuentaBancariaService.saveCuentaBancariaActual(cliente.getId(), Math.random() * 90000, 9000);
					cuentaBancariaService.saveCuentaBancariaAhorro(cliente.getId(), 1200000, 5.5); //guarda un ahorro
					List<CuentaBancaria> cuentaBancarias = cuentaBancariaService.listCuentasBancarias(); //lista las cuentas bancarias

					//recorre la lista de cuentaBancarias
					for (CuentaBancaria cuentaBancaria : cuentaBancarias) {
						for (int i = 0; i < 10; i++) {
							cuentaBancariaService.credit(cuentaBancaria.getId(), 10000 + Math.random() * 120000, "Credito");
							cuentaBancariaService.debit(cuentaBancaria.getId(), 1000 + Math.random() * 9000, "Debito");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		};
	}


	//Prueba el BancoService.java
//@Bean
	CommandLineRunner commandLineRunner(BancoService bancoService) {
		return args -> {
			bancoService.consultar();
		};
	}

	//Agregar datos de prueba: para ejecutar dar clic en esta clase o en el play de public class P4ApiBancaDigitalApplication {
//@Bean  //al comentar Bean no se ejecutará este codigo
	CommandLineRunner start(ClienteRepository clienteRepository, CuentaBancariaRepository cuentaBancariaRepository, OperacionCuentaRepository operacionCuentaRepository) { //agregar los repositories
		return args -> {
			Stream.of("Christian", "Julia", "Biaggio", "Lanudo").forEach(nombre -> {
				Cliente cliente = new Cliente(); // Crea una nueva instancia de la clase Cliente // Crea un nuevo objeto Cliente para representar a un cliente
				//Establece datos de los clientes
				cliente.setNombre(nombre);
				cliente.setEmail(nombre + "@gmail.com");
				//llama al metodo save de clienteRepository para guardar el cliente
				clienteRepository.save(cliente);
			});

			//Asigna cuentas bancarias(Actual y Ahorro) a los clientes anteriores
			clienteRepository.findAll().forEach(cliente -> { // Itera sobre la lista de clientes devuelta por findAll() y recorre por cada cliente

				CuentaActual cuentaActual = new CuentaActual(); // Crea una nueva instancia de la clase CuentaActual
				//Establece datos del objeto cuenta actual del cliente
				cuentaActual.setId(UUID.randomUUID().toString()); //establece el id de la cuenta actual de forma random
				cuentaActual.setBalance(Math.random() * 9000); //establece el balance de la cuenta actual que tiene el cliente
				cuentaActual.setFechaCreacion(new Date()); //establece la fecha actual
				cuentaActual.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaActual.setCliente(cliente);
				cuentaActual.setSobregiro(9000);

				//Agregar los datos alamacenados de la cuentaActual a CuentaBancaria
				cuentaBancariaRepository.save(cuentaActual);

				//Cuenta de Ahorro
				CuentaAhorro cuentaAhorro = new CuentaAhorro(); // Crea una nueva instancia de la clase CuentaAhorros
				//Establece datos del objeto cuenta de ahorros del cliente
				cuentaAhorro.setId(UUID.randomUUID().toString()); //establece el id de la cuenta ahorros de forma random
				cuentaAhorro.setBalance(Math.random() * 9000); //establece el balance de la cuenta de ahorros que tiene el cliente
				cuentaAhorro.setFechaCreacion(new Date()); //establece la fecha actual
				cuentaAhorro.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaAhorro.setCliente(cliente);
				cuentaAhorro.setTasaDeInteres(5.5);

				//Agregar los datos alamacenados de la cuentaAhorros a CuentaBancaria
				cuentaBancariaRepository.save(cuentaAhorro);
			});

			//Operaciones
			//con el forEach se recorre la lista de cuentasBancarias que retorna findAll y por cada cuenta bancaria se realizará operaciones
			cuentaBancariaRepository.findAll().forEach(cuentaBancaria -> {
				for (int i = 0; i < 10; i++) {
					OperacionCuenta operacionCuenta = new OperacionCuenta(); //Crea una nueva instancia de OperacionCuenta llamada operacionCuenta
					//Se establecen los datos a la nueva instancia creada operacionCuenta
					operacionCuenta.setFechaOperacion(new Date());
					operacionCuenta.setMonto(Math.random() * 12000);
					operacionCuenta.setTipoOperacion(Math.random() > 05 ? TipoOperacion.DEBITO : TipoOperacion.CREDITO);//establece el tipo de operacion de forma random si lo que retorna Math.random es mayor a 5 seria debito sino credito
					operacionCuenta.setCuentaBancaria(cuentaBancaria); //se le envia la cuenta bancaria a realizar estas operaciones

					//Agrega los datos almacenados en la operacioncuenta realizada
					operacionCuentaRepository.save(operacionCuenta);
				}

			});

		};
	}




}



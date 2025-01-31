package com.banca.digital.p4_api_banca_digital.repositorios;

import com.banca.digital.p4_api_banca_digital.entidades.OperacionCuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, Long> {

    //Muestra el historial de operaciones de una cuenta
    List<OperacionCuenta> findByCuentaBancariaId(String cuentaId); //busca una cuenta bancaria por su Id

    //Muestra el historial de operaciones de una cuenta con paginacion
    Page<OperacionCuenta> findByCuentaBancariaId(String cuentaId, Pageable pageable);

    Page<OperacionCuenta> findByCuentaBancariaIdOrderByFechaOperacionDesc(String cuentaId, Pageable pageable);

}

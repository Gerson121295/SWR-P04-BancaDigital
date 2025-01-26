package com.banca.digital.p4_api_banca_digital.repositorios;

import com.banca.digital.p4_api_banca_digital.entidades.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, String> { //Recibe la clase CuentaBancaria y su tipo de Id Long
}

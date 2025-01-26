package com.banca.digital.p4_api_banca_digital.repositorios;

import com.banca.digital.p4_api_banca_digital.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

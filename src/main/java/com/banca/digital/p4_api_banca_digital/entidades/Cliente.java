package com.banca.digital.p4_api_banca_digital.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;

    //Un Cliente tiene muchas cuentas bancarias - //Relacion bidireccional
    //el nuevo campo que contiene la relacion se crea en la clase a la que esta dirigida el Many en este caso es @OneToMany por lo tanto seria en CuentaBancaria que esta relacionada por cliente que seria de tipo Cliente lo que generaria la FK automaticamente el campo cliente_id que contiene la relacion entre estas 2 tablas
    //en la clase donde se encuentre el campo a mappear por mappedBy, Si no se usa @JoinColumn, Hibernate asignará un nombre predeterminado como cliente_id
    //mappedBy indica(que es la clase padre principal)
    @OneToMany(mappedBy = "cliente") //mappedBy indica lo contrario en la otra clase CuentaBancaria(seria @ManyToOne "relacion seria bidireccional") que muchas cuentas bancarias le pertenecen al cliente
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //WRITE_ONLY Asegura que al serializar un objeto Cliente a JSON (por ejemplo, en una respuesta de la API), no se incluirá la lista de cuentas bancarias asociadas. Solo los atributos de cliente, sin embargo, esa lista sí puede ser utilizada al recibir un JSON (deserialización) para mapear datos hacia un objeto Cliente.
    private List<CuentaBancaria> cuentasBancarias; //una lista de todas las cuentas bancarias asociadas a un cliente.


    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CuentaBancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(List<CuentaBancaria> cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }
}

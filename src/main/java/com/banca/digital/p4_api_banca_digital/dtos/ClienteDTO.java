package com.banca.digital.p4_api_banca_digital.dtos;


//Patron DTO permite crear un objeto plano(POJO) con una serie de atributos que pueden ser enviados o recuperados del servidor en una sola invocacion
// de tal forma que un DTO puede obtener informacion de multiples fuentes o tablas y concentrarlas en una unica clase simple.

public class ClienteDTO {  //el mapeo de ClienteDTO se realiza en CuentaBancariaMapperImpl

    //Atributos que se requiere de la Clase Cliente.
    private Long id;
    private String nombre;
    private String email;

    //Getters  And Setters
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
}

package com.banca.digital.p4_api_banca_digital.entidades;

import com.banca.digital.p4_api_banca_digital.enums.EstadoCuenta;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity //define que esta clase creara una tabla en la BD si no se agrega el nombre por defecto sera: cuenta_bancaria
//@Inheritance todas las clases de una gerarquia se va a asignar a una sola tabla, todas las clases que van a heredar CuentaBancaria se van asignar a una sola tabla y en un campo en especifico, ese campo se define con DiscriminatorColumn
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)// al definir TABLE_PER_CLASS no se agrega @DiscriminatorColumn y esta clase debe ser abstract y ya no seria una tabla(cuenta_bancaria) que contenga CuentaAhorro y CuentaActual si no que ahora se crearia estas tablas por separado y no se crea cuenta_bancaria
//@Inheritance(strategy = InheritanceType.JOINED)// al definir JOINED no se agrega @DiscriminatorColumn y esta clase debe ser abstract y ya no seria una tabla(cuenta_bancaria) que contenga CuentaAhorro y CuentaActual si no que ahora se crearia estas tablas por separado con sus campos que se definan en su clase ya no tendria herencia del padre y se crea cuenta_bancaria solo con su campos establecidos
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //herencia //definir a esta entidad como singleTable una sola tabla o una sola entidad que se podra colocar varias clases que seran como campos
@DiscriminatorColumn(name = "TIPO", length = 4) //Tipo es un campo de la tabla cuenta_bancaria que estarán las clases(CuentaAhorro y CuentaActual) //columna discriminante
public class CuentaBancaria { //CuentaBancaria es una clase padre si se quiere heredar lo metodos tiene que ser abstract: public abstract class CuentaBancaria

    @Id
    private String id;

    private double balance;
    private Date fechaCreacion;

    @Enumerated(EnumType.STRING) // indica a JPA cómo guardar este valor en la base de datos. STRING  los valores del enum se almacenan como cadenas de texto en la base de datos.
    private EstadoCuenta estadoCuenta;

    //Relacion bidireccional muchas cuentas bancarias le pertenecen a 1 cliente y en la clase Cliente seria OneToMany 1 cliente tiene muchas cuentas bancaria
    @ManyToOne
    //@JoinColumn(name = "cliente_id")    //Si no se usa @JoinColumn, Hibernate asignará un nombre predeterminado como cliente_id.
    private Cliente cliente; //cliente define la relacion con Cliente y generará una FK cliente_id


    //Relacion bidireccional
    //Una cuenta bancaria tiene muchas operaciones en las cuentas, Establece LAZY cuando se llame aparecerá la lista
    @OneToMany(mappedBy = "cuentaBancaria", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY) //mappedBy indica que es la clase padre "dueña de la entidad" por lo tanto indica la relacion entre CuentaBancaria y OperacionCuenta se realizara mediante el atributo definido en OperacionCuenta llamado cuentaBancaria de tipo CuentaBancaria
    private List<OperacionCuenta> operacionesCuenta; //una lista de operacionesCuenta asociada a cuentaBancaria


    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<OperacionCuenta> getOperacionesCuenta() {
        return operacionesCuenta;
    }

    public void setOperacionesCuenta(List<OperacionCuenta> operacionesCuenta) {
        this.operacionesCuenta = operacionesCuenta;
    }
}

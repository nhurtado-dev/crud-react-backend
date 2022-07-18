package com.crud.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name="empleados")
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String contraseña;

}

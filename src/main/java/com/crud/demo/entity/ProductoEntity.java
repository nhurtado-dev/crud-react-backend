package com.crud.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name="productos")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private int stock;
    private double precio;

}

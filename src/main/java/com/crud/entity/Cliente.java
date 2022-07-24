package com.crud.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
// @Table(name = "cliente")
@Table(name = "cliente",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = { "dni" }),
		@UniqueConstraint(columnNames = { "correo" }),
		 })
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCliente;
	
	@NotEmpty(message = "Campo 'Nombre' No puede estar vacio!!!!!")
	@Size(min=4,max=25,message = "El tamaño debe estar entre 4 y 25 caracteres")
	private String nombres;
	
	@NotEmpty(message = "Campo 'Apellido' No puede estar vacio!!!!!")
	@Size(min=4,max=25,message = "El tamaño debe estar entre 4 y 25 caracteres")
	private String apellidos;


	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "America/Lima")
	private Date fechaNacimiento;

	@Size(max=8,min=8,message="Campo 'DNI' debe contener 8 caracteres")
	@NotNull(message = "Campo 'DNI' No puede estar vacio")
	private String dni;
	
	@NotEmpty(message = "Campo 'Correo' No puede estar vacio!!!!!")
	@Email(message = "Debe ingresar un correo válido")
	private String correo;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fechaRegistro;

	@NotNull(message = "Campo 'Dirección' No puede estar vacio")
	private String direccion;

	@Min(value = 0, message = "Campo 'Estado' Solo admite -> 1: Activo; 0:Innactivo")
	@Max(value = 1, message = "Campo 'Estado' Solo admite -> 1: Activo; 0:Innactivo")
	private int estado;

	@NotNull(message = "El Ubigeo es obligatorio")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUbigeo")
	private Ubigeo ubigeo;
	
	public Cliente(int idCliente) {
		this.idCliente = idCliente;
	}
   
}

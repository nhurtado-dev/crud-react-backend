package com.crud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "tipo_reclamo")
@AllArgsConstructor
@NoArgsConstructor
public class TipoReclamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTipoReclamo;
	private String descripcion;
	private int estado;
	
	public TipoReclamo(int idTipoReclamo) {
		this.idTipoReclamo = idTipoReclamo;
	}
	
	
	
}

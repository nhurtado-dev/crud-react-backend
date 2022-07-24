package com.crud.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class PedidoHasProductoPK  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int idPedido;
	private int idProducto;

}

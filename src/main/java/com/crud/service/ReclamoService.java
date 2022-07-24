package com.crud.service;

import java.util.List;

import com.crud.entity.Reclamo;

public interface ReclamoService {
	
	public abstract List<Reclamo> listaReclamo();
	
	public abstract Reclamo insertReclamo(Reclamo reclamo);
	
	public abstract List<Reclamo> filtro(String descripcion, int estado, int idCliente, int idTipoReclamo);
	
	public abstract List<Reclamo> filtro(String fechaRegistro);
	
	public abstract List<Reclamo> filtro(String rangoInicioFecha, String rangoFinFecha);
	
	public abstract boolean update(Reclamo reclamo);
	
	public abstract boolean eliminar(int idReclamo);
	

}

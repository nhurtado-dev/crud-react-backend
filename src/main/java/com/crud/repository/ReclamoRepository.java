package com.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crud.entity.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Integer>{
	
	@Query("SELECT x FROM Reclamo x "
			+ " WHERE "
			+ " x.descripcion LIKE CONCAT('%',:descripcion,'%') AND "
			+ " x.estado = :estado AND "
			+ " (:idCliente IS -1 OR x.cliente.idCliente = :idCliente) AND "
			+ " (:idTipoReclamo IS -1 OR x.tipoReclamo.idTipoReclamo = :idTipoReclamo)")
	public abstract List<Reclamo> filtro(String descripcion, int estado, int idCliente, int idTipoReclamo);
	
	@Query("SELECT r FROM Reclamo r "
			+ " WHERE "
			+ " r.fechaRegistro LIKE CONCAT(:fechaRegistro,'%')")		
	public abstract List<Reclamo> filtro(String fechaRegistro);
	
	@Query("SELECT r FROM Reclamo r "
			+ " WHERE "
			+ "r.fechaRegistro BETWEEN CONCAT(:rangoInicioFecha,'%') AND CONCAT(:rangoFinFecha,'%')")
	public abstract List<Reclamo> filtro(String rangoInicioFecha, String rangoFinFecha);

}

package com.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crud.entity.Proveedor;



public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
	
	@Query("Select e from Proveedor e where(?1 is '' or e.razonsocial like ?1) "
			+ "and (?2 is '' or e.ruc like ?2) "
			+ "and (?3 is -1 or e.ubigeo.idUbigeo = ?3) "
			+ "and e.estado = ?4")
	public  List<Proveedor> Filtrar(String razonsocial, String ruc, int idUbigeo, int estado);



}

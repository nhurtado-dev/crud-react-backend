package com.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crud.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Query("select x  from Cliente x "
			+ " where "
			+ " CONCAT(x.nombres,' ',x.apellidos) LIKE CONCAT('%',:nombre,'%') and "
			+ " x.dni LIKE CONCAT('%',:dni,'%') and "
			+ " x.correo LIKE CONCAT('%',:correo,'%') and "
			+ "	(:idUbigeo is -1 or x.ubigeo.idUbigeo = :idUbigeo) and "
			+ "	x.estado = :estado")
	public List<Cliente> filtro(String nombre, String correo, String dni, int idUbigeo,int estado);


}

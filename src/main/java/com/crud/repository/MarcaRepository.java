package com.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crud.entity.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer>{
	
	@Query("Select m from Marca m where m.nombre like :var_param")
	public abstract List<Marca> findAllMarcas(@Param("var_param") String nombre);
	
	
	@Query("select m from Marca m where m.nombre like CONCAT('%',:nombre,'%') and"
			+ "(:pais is -1 or m.pais.idPais = :pais) and "
			+ " m.estado = :estado and "
			+ " m.descripcion like concat ('%',:descripcion,'%')")
	public List<Marca> filtro(String nombre, int pais, int estado, String descripcion);
	
	
}

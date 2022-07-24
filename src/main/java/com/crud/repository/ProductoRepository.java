package com.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crud.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	@Query("Select p from Producto p where p.nombre like :var_param")
	public abstract List<Producto> findAllProductos(@Param("var_param") String nombre);
	
	
	@Query("select p from Producto p where p.nombre like CONCAT('%',:nombre,'%') and"
			+ "(:pais is -1 or p.pais.idPais = :pais) and "
			+ "(:marca is -1 or p.marca.idMarca = :marca) and "
			+ " p.estado = :estado")
	public List<Producto> filtro(String nombre, int pais, int marca, int estado);

}

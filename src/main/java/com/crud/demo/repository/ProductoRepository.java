package com.crud.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.demo.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	

}

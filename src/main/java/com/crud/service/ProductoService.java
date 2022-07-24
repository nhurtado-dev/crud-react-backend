package com.crud.service;

import java.util.List;
import java.util.Optional;

import com.crud.entity.Producto;

public interface ProductoService {
	
	public abstract List<Producto> listaProducto();
	
	public abstract Producto insertProducto(Producto pro);
	
	public abstract List<Producto> findAllProductos(String nombre);
	
	public abstract Optional<Producto> buscaPorId(int id);
	
	public abstract List<Producto> filtro(String nombre, int pais, int marca, int estado);
	
	public abstract Producto update(Producto pro,int id);
	
	public abstract Producto eliminar(int id);

}

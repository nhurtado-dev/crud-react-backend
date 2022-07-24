package com.crud.service;

import java.util.List;
import java.util.Optional;

import com.crud.entity.Marca;

public interface MarcaService {

	public abstract List<Marca> listaMarca();

	public abstract Marca saveMarca(Marca obj);
	
	public abstract List<Marca> findAllMarcas(String nombre);
	
	public abstract Optional<Marca> buscaPorId(int id);
	
	public abstract List<Marca> filtro(String nombre, int pais, int estado, String descripcion);
	
	public abstract Marca update(Marca mar, int id);
	public abstract Marca eliminar(int id);
}

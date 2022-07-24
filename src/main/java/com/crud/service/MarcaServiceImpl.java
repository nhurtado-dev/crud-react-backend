package com.crud.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.entity.Marca;
import com.crud.repository.MarcaRepository;

@Service
public class MarcaServiceImpl implements MarcaService {

	@Autowired
	private MarcaRepository Repository;

	@Override
	public List<Marca> listaMarca() {
		return Repository.findAll();
	}

	@Override
	public Marca saveMarca(Marca obj) {
		obj.setFechaRegistro(new Date());
		obj.setEstado(1);
		return Repository.save(obj);
	}

	@Override
	public List<Marca> findAllMarcas(String nombre) {
		
		return Repository.findAllMarcas(nombre);
	}

	@Override
	public Optional<Marca> buscaPorId(int id) {
		
		return Repository.findById(id);
	}

	@Override
	public List<Marca> filtro(String nombre, int pais, int estado, String descripcion) {
		// TODO Auto-generated method stub
		return Repository.filtro(nombre, pais, estado, descripcion);
	}

	@Override
	public Marca update(Marca mar, int id) {
		Marca marcaUpdated = findById(id);
		if(marcaUpdated==null) {
			return null;
		}
		return Repository.save(mar);
	}

	@Override
	public Marca eliminar(int id) {
		Marca marcaDeleted = findById(id);
		if(marcaDeleted==null) {
			return null;
		}
		Repository.deleteById(id);
		return marcaDeleted;
	}
	
	private Marca findById(int id) {
		return Repository.findById(id).orElse(null);
	}

}

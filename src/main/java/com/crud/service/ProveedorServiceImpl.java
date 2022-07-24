package com.crud.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.entity.Proveedor;
import com.crud.repository.ProveedorRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService{

	
	@Autowired
	private ProveedorRepository repository;
	
	@Override
	public List<Proveedor> listaProveedor() {
		
		return repository.findAll();
	}

	@Override
	public Proveedor GuardarProveedor(Proveedor obj) {
		obj.setFechaRegistro(new Date());

		obj.setEstado(1);

		return repository.save(obj);
	}

	@Override
	public List<Proveedor> Filtrar(String razonsocial, String ruc, int idUbigeo, int estado) {
		return repository.Filtrar(razonsocial, ruc, idUbigeo, estado);
	}

	@Override
	public Proveedor actualizar(Proveedor pro, int id) {
		Proveedor proveedorAct = findById(id);
		if(proveedorAct == null) {
			return null;
		}
		return repository.save(pro);
	}

	@Override
	public Proveedor eliminar(int id) {
		Proveedor proveedorEli = findById(id);
		if(proveedorEli == null) {
			return null;
		}
		repository.deleteById(id);
		return proveedorEli;
	}
	

	
	private Proveedor  findById(int id) {
		return repository.findById(id).orElse(null);
	}

}

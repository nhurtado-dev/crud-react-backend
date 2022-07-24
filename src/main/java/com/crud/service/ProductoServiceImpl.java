package com.crud.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.entity.Producto;
import com.crud.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
	
	@Autowired
	private ProductoRepository repository;

	@Override
	public List<Producto> listaProducto() {
		return repository.findAll();
	}

	@Override
	public Producto insertProducto(Producto pro) {
		pro.setEstado(1);
		pro.setFechaRegistro(new Date());
		return repository.save(pro);
	}

	@Override
	public List<Producto> findAllProductos(String nombre) {
		// TODO Auto-generated method stub
		return repository.findAllProductos(nombre);
	}

	@Override
	public Optional<Producto> buscaPorId(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Producto> filtro(String nombre, int pais, int marca, int estado) {
		// TODO Auto-generated method stub
		return repository.filtro(nombre, pais, marca, estado);
	}
	
	@Override
	public Producto update(Producto pro,int id) {
		Producto productoUpd=findById(id);
		if(productoUpd==null) {
			return null;
		}
		return repository.save(pro);
	}

	@Override
	public Producto eliminar(int id) {
		Producto productoDel=findById(id);
		if(productoDel==null) {
			return null;
		}
		repository.deleteById(id);
		return productoDel;
	}

	private Producto findById(int id) {
		return repository.findById(id).orElse(null);
	}
}

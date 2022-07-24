package com.crud.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.entity.Reclamo;
import com.crud.repository.ReclamoRepository;

@Service
public class ReclamoServiceImpl implements ReclamoService{

	@Autowired
	private ReclamoRepository repository;
	
	@Override
	public List<Reclamo> listaReclamo() {
		return repository.findAll();
	}

	@Override
	public Reclamo insertReclamo(Reclamo reclamo) {
		reclamo.setEstado(1);
		reclamo.setFechaRegistro(new Date());
		System.out.println("entre a insertar reclamo, fecha registro -> " + reclamo.getFechaRegistro());
		return repository.save(reclamo);
	}

	@Override
	public List<Reclamo> filtro(String descripcion, int estado, int idCliente, int idTipoReclamo) {
		// TODO Auto-generated method stub
		return repository.filtro(descripcion, estado, idCliente, idTipoReclamo);
	}

	@Override
	public List<Reclamo> filtro(String fechaRegistro) {
		// TODO Auto-generated method stub
		return repository.filtro(fechaRegistro);
	}

	@Override
	public List<Reclamo> filtro(String rangoInicioFecha, String rangoFinFecha) {
		// TODO Auto-generated method stub
		return repository.filtro(rangoInicioFecha, rangoFinFecha);
	}

	@Override
	public boolean update(Reclamo reclamo) {
		// TODO Auto-generated method stub
		Reclamo existeReclamo = repository.findById(reclamo.getIdReclamo()).orElse(null);
		if(existeReclamo == null) {			
			return false;
		}else {
			repository.save(reclamo);
			return true;
		}
	}

	@Override
	public boolean eliminar(int idReclamo) {
		// TODO Auto-generated method stub
		Reclamo existeReclamo = repository.findById(idReclamo).orElse(null);
		if(existeReclamo == null) {			
			return false;
		}else {
			repository.deleteById(idReclamo);
			return true;
		}
	}

	
}

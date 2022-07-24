package com.crud.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.entity.Cliente;
import com.crud.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Override
	public List<Cliente> listaCliente() {
		return repository.findAll();
	}

	@Override
	public Cliente insertCliente(Cliente cli) {
		cli.setFechaRegistro(new Date());
		return repository.save(cli);
	}
	@Override
	public List<Cliente> filtro(String nombre, String correo, String dni, int idUbigeo, int estado) {
		// TODO Auto-generated method stub
		return repository.filtro(nombre,correo, dni, idUbigeo,estado);
	}

	@Override
	public Cliente update(Cliente cli,int id) {
		Cliente clienteUpd=findById(id);
		if(clienteUpd==null) {
			return null;
		}
		return repository.save(cli);
	}

	@Override
	public Cliente eliminar(int id) {
		Cliente clienteDel=findById(id);
		if(clienteDel==null) {
			return null;
		}
		repository.deleteById(id);
		return clienteDel;
	}
	
	private Cliente findById(int id) {
		return repository.findById(id).orElse(null);
	}
	
	
	
}

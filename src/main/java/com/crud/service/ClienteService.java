package com.crud.service;

import java.util.List;

import com.crud.entity.Cliente;

public interface ClienteService{

	public abstract List<Cliente> listaCliente();
	
	public abstract Cliente insertCliente(Cliente cli);
	public abstract Cliente update(Cliente cli,int id);
	public abstract Cliente eliminar(int id);
	public abstract List<Cliente> filtro(String nombre, String correo, String dni,  int idUbigeo, int estado);
}

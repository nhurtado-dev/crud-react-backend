package com.crud.service;

import java.util.List;

import com.crud.entity.Proveedor;


public interface ProveedorService {

	public abstract List<Proveedor> listaProveedor();
	
	public abstract Proveedor GuardarProveedor(Proveedor obj);
	
	public abstract Proveedor actualizar(Proveedor pro,int id);
	public abstract Proveedor eliminar(int id);
	
	public abstract  List<Proveedor> Filtrar(String razonsocial, String ruc, int idUbigeo, int estado);

	
}

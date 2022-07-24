package com.crud.service;

import java.util.List;

import com.crud.entity.Opcion;
import com.crud.entity.Rol;
import com.crud.entity.Usuario;

public interface UsuarioService {

	public abstract Usuario login(Usuario bean);

	public abstract List<Opcion> traerEnlacesDeUsuario(int idUsuario);

	public abstract List<Rol> traerRolesDeUsuario(int idUsuario);

	public abstract Usuario buscaPorLogin(String login);
}

package com.crud.service;

import java.util.List;
import java.util.Optional;

import com.crud.entity.Pais;

public interface PaisService {

	public abstract List<Pais> listaPais();
	
	public abstract Optional<Pais> listaPaisId(int id);

}

package com.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.entity.Pais;
import com.crud.repository.PaisRepository;

@Service
public class PaisServiceImp implements PaisService {

	@Autowired
	private PaisRepository Repository;

	@Override
	public List<Pais> listaPais() {
		return Repository.findAll();

	}

	@Override
	public Optional<Pais> listaPaisId(int id) {
		// TODO Auto-generated method stub
		return Repository.findById(id);
	}

}

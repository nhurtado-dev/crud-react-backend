package com.crud.demo.services;

import com.crud.demo.entity.EmpleadoEntity;
import com.crud.demo.model.Empleado;
import com.crud.demo.repository.EmpleadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    private EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        EmpleadoEntity empleadoEntity = new EmpleadoEntity();
        BeanUtils.copyProperties(empleado, empleadoEntity);
        empleadoRepository.save(empleadoEntity);
        return empleado;
    }
}

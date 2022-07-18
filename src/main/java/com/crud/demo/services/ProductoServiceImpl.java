package com.crud.demo.services;

import com.crud.demo.entity.ProductoEntity;
import com.crud.demo.model.Producto;
import com.crud.demo.repository.ProductoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService{

    private ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto crearProducto(Producto producto) {
        ProductoEntity productoEntity = new ProductoEntity();
        BeanUtils.copyProperties(producto, productoEntity);
        productoRepository.save(productoEntity);
        return producto;
    }

}

package com.crud.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.exception.ResourceNotFoundException;
import com.crud.demo.model.Producto;
import com.crud.demo.repository.ProductoRepository;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class ProductoController {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@GetMapping("/productos")
	public List<Producto> getAllProductos(){
		return productoRepository.findAll();
	}
	
	@PostMapping("/productos")
	public Producto createProducto(@RequestBody Producto producto) {
		return productoRepository.save(producto);
	}
	
	@GetMapping("/productos/{id}")
	public ResponseEntity<Producto> getProductoById(@PathVariable Long id){
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El producto con ID: " + id + " no existe."));
		return ResponseEntity.ok(producto);
	}
	
	@PutMapping("/productos/{id}")
	public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetalles){
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El producto con ID: " + id + " no existe."));
		
		producto.setId(productoDetalles.getId());
		producto.setNombre(productoDetalles.getNombre());
		producto.setStock(productoDetalles.getStock());
		producto.setPrecio(productoDetalles.getPrecio());
		producto.setEstado(productoDetalles.isEstado());
		
		Producto updatedProducto = productoRepository.save(producto);
		return ResponseEntity.ok(updatedProducto);
	}
	
	@DeleteMapping("/productos/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProducto(@PathVariable Long id){
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("El producto con ID: " + id + " no existe."));
		
		productoRepository.delete(producto);
		Map<String, Boolean> response = new HashMap<>();
		response.put("El producto con ID" + id + " ha sido eliminado", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}

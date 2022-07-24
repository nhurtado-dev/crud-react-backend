package com.crud.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.entity.Producto;
import com.crud.service.ProductoService;
import com.crud.util.AppSettings;

@RestController
@RequestMapping("/url/producto")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping
	public ResponseEntity<List<Producto>> listaProducto(){
		List<Producto> lista = productoService.listaProducto();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping("/crear")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody Producto producto, BindingResult result){
		Producto productoNew = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if (result.hasErrors()) {
			Map<String, String> data = new HashMap<>();			
			result.getFieldErrors().forEach((error) -> {
				String nombreCampo = ((FieldError) error).getField();
				String mensaje = error.getDefaultMessage();
				data.put(nombreCampo, mensaje);
			});
			response.put("type", "error");
			response.put("message", "Validación fallida");
			response.put("data", data);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			producto.setEstado(1);
			producto.setFechaRegistro(new Date());
			productoNew = productoService.insertProducto(producto);
			if (productoNew == null) {
				response.put("type", "error");
				response.put("message", "No se registró correctamente, por favor revise los datos.");
				response.put("data", producto);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			response.put("type", "error");
			response.put("message", "Ocurrió un error al insertar los datos en la BD.");
			response.put("data", e.getMessage() + ":" + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			response.put("type", "error");
			response.put("message", "Error BAD REQUEST");
			response.put("data", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.put("type", "success");
		response.put("message", "Registro exitoso");
		response.put("data", productoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
	
	@GetMapping("/filtro")
	public ResponseEntity<Map<String, Object>> listaFiltrada(@RequestParam(name = "nombre", required=false, defaultValue = "") String nombre,
			@RequestParam(name = "pais", required = false, defaultValue="-1") int pais,
			@RequestParam(name = "marca", required = false, defaultValue="-1") int marca,
			@RequestParam(name = "estado", required = false, defaultValue="1") int estado
			){
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Producto> lista = productoService.filtro(nombre, pais, marca, estado);
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar");
				
			}else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " para mostrar");
			}
		} catch (Exception e) {
			salida.put("mensaje", e.getMessage());
		}
		
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping("actualizar/{id}")
	public ResponseEntity<Map<String, Object>> actualizaProducto(@Valid @RequestBody Producto producto, BindingResult result,
			@PathVariable(name = "id") int id) {
		Map<String, Object> response = new HashMap<String, Object>();

		if (result.hasErrors()) {
			Map<String, String> data = new HashMap<>();			
			result.getFieldErrors().forEach((error) -> {
				String nombreCampo = ((FieldError) error).getField();
				String mensaje = error.getDefaultMessage();
				data.put(nombreCampo, mensaje);
			});
			response.put("type", "error");
			response.put("message", "Validación de campos fallida");
			response.put("data", data);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		Producto productoUpd = null;
		try {

			productoUpd = productoService.update(producto,id);
			if (productoUpd == null) {
				response.put("type", "error");
				response.put("message", "No se encontro el producto con id: "+id);
				response.put("data", null);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			response.put("type", "error");
			response.put("message", "Error al realizar la actualización en la base de datos!");
			response.put("data", e.getMessage() + ":" + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			response.put("type", "error");
			response.put("message", "Error general!");
			response.put("data", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		response.put("type", "success");
		response.put("message", "Actualización exitoso");
		response.put("data", productoUpd);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Map<String, Object>> elimina(@PathVariable("id")int id) {
		Map<String, Object> response = new HashMap<String, Object>();

		Producto productoDel = null;
		try {

			productoDel = productoService.eliminar(id);
			if (productoDel == null) {
				response.put("type", "error");
				response.put("message", "No se encontro el producto con id: "+id);
				response.put("data", null);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			response.put("type", "error");
			response.put("message", "Error al eliminar de la base de datos!");
			response.put("data", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			response.put("type", "error");
			response.put("message", "Error general!");
			response.put("data", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		response.put("type", "success");
		response.put("message", "Eliminación exitosa");
		response.put("data", productoDel);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);


	}

}

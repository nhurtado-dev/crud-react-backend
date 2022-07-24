package com.crud.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.entity.Marca;
import com.crud.service.MarcaService;
import com.crud.util.AppSettings;

@RestController
@RequestMapping("/url/marca")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class MarcaController {
	
	@Autowired
	private MarcaService marcaService;
	
	
	@GetMapping
	public ResponseEntity<List<Marca>> listaMarca(){
		List<Marca> lista = marcaService.listaMarca();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/filtro")
	public ResponseEntity<Map<String, Object>> listaFiltrada(@RequestParam(name = "nombre", required=false, defaultValue = "") String nombre,
			@RequestParam(name = "pais", required = false, defaultValue="-1") int pais,
			@RequestParam(name = "estado", required = false, defaultValue="1") int estado,
			@RequestParam(name = "descripcion", required = false, defaultValue="") String descripcion
			){
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Marca> lista = marcaService.filtro(nombre, pais, estado, descripcion);
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
	
	
	
	
	@PostMapping("/crear")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> PostMarca(@RequestBody Marca obj){
		
		HashMap<String, Object> salida = new HashMap<>();
		try {
			List<Marca> lista = marcaService.findAllMarcas(obj.getNombre());
			if(CollectionUtils.isEmpty(lista)) {
				Marca saveMarca = marcaService.saveMarca(obj);
				if(saveMarca == null) {
					salida.put("message", "Rellene todos los Datos");
				}else {
					salida.put("message", "La marca " + obj.getNombre() + " se ha registrado correctamente");
				} 
			}else {
				salida.put("message", "La marca " + obj.getNombre() + " ya existe");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("message", "Error en el registro: " + e.getMessage());
		}
		
		
		return ResponseEntity.ok(salida);
	}
	
	@PutMapping("actualizar/{id}")
	public ResponseEntity<Map<String,Object>> actualizarMarca(@Valid @RequestBody Marca marca, BindingResult result,
			@PathVariable(name = "id") int id){
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(result.hasErrors()) {
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
		
		Marca marcaUpdated = null;
		try {
			marcaUpdated =  marcaService.update(marca, id);
			if(marcaUpdated == null) {
				response.put("type", "error");
				response.put("message", "No se encontro la marca con id: "+id);
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
		response.put("data", marcaUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable("id")int id){
		Map<String, Object> response = new HashMap<String, Object>();
		
		Marca marcaDeleted = null;
		try {
			marcaDeleted = marcaService.eliminar(id);
			if(marcaDeleted == null) {
				response.put("type", "error");
				response.put("message", "No se encontro la marca con id: "+id);
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
		response.put("message", "Eliminación exitoso");
		response.put("data", marcaDeleted);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
			
	
}

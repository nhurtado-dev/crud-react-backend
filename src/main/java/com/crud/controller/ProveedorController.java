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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.entity.Proveedor;
import com.crud.service.ProveedorService;
import com.crud.util.AppSettings;

@RestController
@RequestMapping("/url/proveedor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ProveedorController {
	
	@Autowired
	private ProveedorService service;

	@GetMapping 
	@ResponseBody
	public ResponseEntity<List<Proveedor>> listaProveedor(){
		List<Proveedor> lista = service.listaProveedor();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/Filtrar") 
	public ResponseEntity<Map<String, Object>> Filtrar(
			@RequestParam(name = "razonsocial", required = false, defaultValue = "") String razonsocial,
			@RequestParam(name = "ruc", required = false, defaultValue = "") String ruc,
			@RequestParam(name = "idUbigeo", required = false, defaultValue = "-1") int idUbigeo,
			@RequestParam(name = "estado", required = false, defaultValue = "1") int estado) {
		System.out.println(razonsocial+"|"+ruc+"|"+idUbigeo+"|"+estado);
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Proveedor> lista = service.Filtrar("%"+razonsocial+"%", ruc, idUbigeo, estado);
			if (CollectionUtils.isEmpty(lista)) {
				salida.put("mensaje", "No existen datos para mostrar");
			}else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " para mostrar");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			salida.put("mensaje", e.getMessage());
		}

		return ResponseEntity.ok(salida);

	}
	
	@PostMapping ("/Registrar")
	@ResponseBody
	public ResponseEntity<HashMap<String, Object>> GuardarProveedor(@RequestBody Proveedor obj){
		
		HashMap<String, Object> salida = new HashMap<String, Object>();
		try {
				Proveedor objsalida = service.GuardarProveedor(obj);
				if(objsalida == null) {
					salida.put("mensaje", "Rellene todos los Datos");
				}else {
					salida.put("mensaje", "El proveedor con el RUC: " + obj.getRuc() + "ser registro correctamente");
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", "Error en el regitro" + e.getMessage());
		}
		return ResponseEntity.ok(salida);
		
	}
	
	@PutMapping("actualizar/{id}")
	public ResponseEntity<Map<String, Object>> actualizaProveedor(@Valid @RequestBody Proveedor proveedor, BindingResult result,
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
		
		Proveedor proveedorAct = null;
		try {
			
			proveedorAct = service.actualizar(proveedor,id);
			if (proveedorAct == null) {
				response.put("type", "error");
				response.put("message", "Proveedor con id: "+ id + "no fue encontrado");
				response.put("data", null);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			response.put("type", "error");
			response.put("message", "Error al actualizar");
			response.put("data", e.getMessage() + ":" + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			response.put("type", "error");
			response.put("message", "Error general!");
			response.put("data", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.put("type", "success");
		response.put("message", "Proveedor con id: "+ id + "fue actulizado correctamente");
		response.put("data", proveedorAct);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Map<String, Object>> elimina(@PathVariable("id")int id) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		Proveedor proveedorEli = null;
		try {
			
			proveedorEli = service.eliminar(id);
			if (proveedorEli == null) {
				response.put("type", "error");
				response.put("message", "No se encontro el proveedor con id: "+id);
				response.put("data", null);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			response.put("type", "error");
			response.put("message", "Error al eliminar!");
			response.put("data", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			response.put("type", "error");
			response.put("message", "Error general!");
			response.put("data", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.put("type", "success");
		response.put("message", "Proveedor Eliminado");
		response.put("data", proveedorEli);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		

	}
	
	
	

	

}

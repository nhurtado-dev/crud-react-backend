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

import com.crud.entity.Cliente;
import com.crud.service.ClienteService;
import com.crud.util.AppSettings;

@RestController
@RequestMapping("/url/cliente")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.listaCliente();
	}
	
	@GetMapping("/filtro") 
	public ResponseEntity<Map<String, Object>> listaDocenteNombreDniUbigeo(
			@RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(name = "correo", required = false, defaultValue = "") String correo,
			@RequestParam(name = "dni", required = false, defaultValue = "") String dni,
			@RequestParam(name = "idUbigeo", required = false, defaultValue = "-1") int idUbigeo,
			@RequestParam(name = "estado", required = false, defaultValue = "1") int estado) {
		System.out.println(nombre+"|"+correo+"|"+dni+"|"+idUbigeo+"|"+estado);
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Cliente> lista = clienteService.filtro(nombre,correo, dni, idUbigeo,estado);
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

	@PostMapping("/crear")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody Cliente cliente, BindingResult result){
		Cliente clienteNew = null;
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
		
		try {
			cliente.setEstado(1);
			cliente.setFechaRegistro(new Date());
			clienteNew = clienteService.insertCliente(cliente);
			if (clienteNew == null) {
				response.put("type", "error");
				response.put("message", "No se registró, consulte con el administrador.");
				response.put("data", cliente);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (DataAccessException e) {
			response.put("type", "error");
			response.put("message", "Error al realizar el insert en la base de datos!");
			response.put("data", e.getMessage() + ":" + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			response.put("type", "error");
			response.put("message", "Error general!");
			response.put("data", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.put("type", "success");
		response.put("message", "Registro exitoso");
		response.put("data", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

	@PutMapping("actualziar/{id}")
	public ResponseEntity<Map<String, Object>> actualizaDocente(@Valid @RequestBody Cliente cliente, BindingResult result,
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
		
		Cliente clienteUpd = null;
		try {
			
			clienteUpd = clienteService.update(cliente,id);
			if (clienteUpd == null) {
				response.put("type", "error");
				response.put("message", "No se encontro el cliente con id: "+id);
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
		response.put("data", clienteUpd);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Map<String, Object>> elimina(@PathVariable("id")int id) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		Cliente clienteDel = null;
		try {
			
			clienteDel = clienteService.eliminar(id);
			if (clienteDel == null) {
				response.put("type", "error");
				response.put("message", "No se encontro el cliente con id: "+id);
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
		response.put("data", clienteDel);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		

	}
}

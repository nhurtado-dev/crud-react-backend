package com.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.dao.DataAccessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.crud.entity.Reclamo;
import com.crud.service.ReclamoService;
import com.crud.util.AppSettings;

@RestController
@RequestMapping("/url/reclamo")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ReclamoController {
	
	@Autowired
	private ReclamoService reclamoService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Reclamo>> listaReclamo(){
		List<Reclamo> lista = reclamoService.listaReclamo();
		return ResponseEntity.ok(lista);
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<?> insertarReclamo(@Valid @RequestBody Reclamo reclamo, BindingResult result){
		Reclamo reclamoNew = null;
		Map<String, Object> salida = new HashMap<String, Object>();
		if(result.hasErrors()) {
			Map<String, String> data = new HashMap<>();			
			result.getFieldErrors().forEach((error) ->{
				String nombreCampo = ((FieldError) error).getField();
				String mensaje = error.getDefaultMessage();
				data.put(nombreCampo, mensaje);
			});
			salida.put("type", "error");
			salida.put("message", "Validación de campos fallida");
			salida.put("data", data);
			return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.BAD_REQUEST);
		}
		
		try {
			Reclamo reclamoSalida = reclamoService.insertReclamo(reclamo);
			if (reclamoSalida == null) {
				salida.put("type", "error");
				salida.put("message", "No se registró, consulte con el administrador.");
				salida.put("data", reclamo);
				return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.BAD_REQUEST);
			}
		}
		catch (DataAccessException e){
			salida.put("type", "error");
			salida.put("message", "Error al realizar el insert en la base de datos!");
			salida.put("data", e.getMessage() + ":" + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			salida.put("type", "error");
			salida.put("message", "Error general!");
			salida.put("data", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.BAD_REQUEST);
		}
		salida.put("type", "success");
		salida.put("message", "Registro exitoso");
		salida.put("data", reclamoNew);
		return new ResponseEntity<Map<String, Object>>(salida, HttpStatus.CREATED);
	}

	@GetMapping("/filtro")
	public ResponseEntity<Map<String, Object>> listarDescripcionFechaRegistroClienteTipoDescipcion(
			@RequestParam(name = "descripcion", required = false, defaultValue = "") String descripcion,
			@RequestParam(name = "fechaRegistro", required = false, defaultValue = "") String fechaRegistro,
			@RequestParam(name = "rangoInicioFecha", required = false, defaultValue = "") String rangoInicioFecha,
			@RequestParam(name = "rangoFinFecha", required = false, defaultValue = "") String rangoFinFecha,
			@RequestParam(name = "estado", required = false, defaultValue = "1") int estado,
			@RequestParam(name = "idCliente", required = false, defaultValue = "-1") int idCliente,
			@RequestParam(name = "idTipoReclamo", required = false, defaultValue = "-1") int idTipoReclamo
		){
		System.out.println("|Nombre -> "+descripcion+" |FechaRegistro -> "+fechaRegistro+" |RangoFechas -> "+rangoInicioFecha+"-"+rangoFinFecha+" |Estado -> "+estado+" |IdCliente -> "+idCliente+" |IdTipoCliente -> "+idTipoReclamo);
		Map<String, Object> salida = new HashMap<>();
		try {
			List<Reclamo> lista = null;
			
			if(!fechaRegistro.isEmpty()) {
				System.out.println("Entre en fecha registro -> "+ fechaRegistro);
				lista = reclamoService.filtro(fechaRegistro);
				System.out.println("Lista->"+lista);
			}else if (!rangoInicioFecha.isEmpty() && !rangoFinFecha.isEmpty()) {
				System.out.println("Entre en rango de fechas");
				System.out.println("inicio fecha -> "+rangoInicioFecha);
				System.out.println("fin fecha -> "+rangoFinFecha);
				lista = reclamoService.filtro(rangoInicioFecha, rangoFinFecha);
				System.out.println("Lista -> " + lista);
			}else {
				System.out.println("Entre en otros datos");
				System.out.println("descripcion-> "+descripcion);
				System.out.println("estado-> "+estado);
				System.out.println("idCliente-> "+idCliente);
				System.out.println("idTipoReclamo-> "+idTipoReclamo);
				lista = reclamoService.filtro(descripcion, estado, idCliente, idTipoReclamo);
				System.out.println("Lista -> " + lista);
			}
			
			if(CollectionUtils.isEmpty(lista)) {
				System.out.println("Lista linea127 ->"+lista);
				salida.put("mensaje", "No existen datos para mostrar");
			}else {
				salida.put("lista", lista);
				salida.put("mensaje", "Existen " + lista.size() + " para mostrar");
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			salida.put("Mensaje error", e.getMessage());
		}
		return ResponseEntity.ok(salida);
		//return null;
	}
	
	@PutMapping("actualizarReclamo")
	public ResponseEntity<Map<String, Object>> actualizarReclamo(@Valid @RequestBody Reclamo reclamo, BindingResult result){
		
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
		
		boolean reclamoActualizado;
		
		try {
			reclamoActualizado = reclamoService.update(reclamo);
			if(reclamoActualizado == false) {
				response.put("type", "error");
				response.put("message", "No se encontró el cliente con id: " + reclamo.getIdReclamo());
				response.put("data", null);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		}catch (DataAccessException e) {
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
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("eliminarReclamo/{id}")
	public ResponseEntity<Map<String, Object>> eliminarReclamo(@PathVariable("id")int id) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		boolean reclamoEliminado;
		try {
			reclamoEliminado = reclamoService.eliminar(id);
			if (reclamoEliminado == false) {
				response.put("type", "error");
				response.put("message", "No se encontro el reclamo con id: "+id);
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
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}

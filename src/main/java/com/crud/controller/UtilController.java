package com.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.entity.Cliente;
import com.crud.entity.Marca;
import com.crud.entity.Pais;
import com.crud.entity.Producto;
import com.crud.entity.TipoReclamo;
import com.crud.entity.Ubigeo;
import com.crud.service.ClienteService;
import com.crud.service.MarcaService;
import com.crud.service.PaisService;
import com.crud.service.ProductoService;
import com.crud.service.TipoReclamoService;
import com.crud.service.UbigeoService;
import com.crud.util.AppSettings;

@RestController
@RequestMapping("/url/util")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class UtilController {

	@Autowired
	private UbigeoService ubigeoService;

	@Autowired
	private PaisService paisService;

	@Autowired
	private MarcaService marcaService;
	
	@Autowired
	private ProductoService productoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private TipoReclamoService tipoReclamoService;

	@GetMapping("/listaCliente")
	@ResponseBody
	public List<Cliente> listaCliente() {
		return clienteService.listaCliente();
	}

	@GetMapping("/listaTipoReclamo")
	@ResponseBody
	public List<TipoReclamo> listaTipoReclamo() {
		return tipoReclamoService.listaTipoReclamo();
	}

	@GetMapping("/listaPais")
	@ResponseBody
	public List<Pais> listaPais() {
		return paisService.listaPais();
	}

	@GetMapping("/listaMarca")
	@ResponseBody
	public List<Marca> listaMarca() {
		return marcaService.listaMarca();
	}
	
	@GetMapping("/listaProducto")
	@ResponseBody
	public List<Producto> listaProducto() {
		return productoService.listaProducto();
	}

	@GetMapping("/listaDepartamentos")
	@ResponseBody
	public List<String> verDepartamentos() {
		return ubigeoService.listaDepartamentos();
	}

	@GetMapping("/listaProvincias/{paramDepar}")
	@ResponseBody
	public List<String> verProvincias(@PathVariable("paramDepar") String departamento) {
		return ubigeoService.listaProvincias(departamento);
	}

	@GetMapping("/listaDistritos/{paramDepar}/{paramProv}")
	@ResponseBody
	public List<Ubigeo> verDistritos(@PathVariable("paramDepar") String departamento, @PathVariable("paramProv") String provincia) {
		return ubigeoService.listaDistritos(departamento, provincia);
	}

}

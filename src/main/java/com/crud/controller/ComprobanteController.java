package com.crud.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.util.AppSettings;

@RestController
@RequestMapping("/url/comprobante")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ComprobanteController {

}

/*
 * @(#) OrderController.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.aval.order.dto.Request;
import com.aval.order.dto.ResponseObject;
import com.aval.order.exception.NotVerifiedSignedJWTException;
import com.aval.order.exception.SignedJWTNullException;
import com.aval.order.service.ResponseObjectService;
import com.nimbusds.jose.JOSEException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Clase de controlador rest para pedidos
 * 
 * @author Andrés Motavita
 *
 */
@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Sistema de Pedidos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrderController {
	/**
	 * Servicio de pedidos
	 */
	@Autowired
	private ResponseObjectService responseObjectService;
	/**
	 * Objeto de consumo de servicios
	 */
	@Autowired
	private RestTemplate restTemplate;
	/**
	 * URL de servicio
	 */
	@Value("${app.auth.url}")
	private String tokenURL;

	/**
	 * Guarda un pedido.
	 * 
	 * @param input
	 * @param request
	 * @return Objeto ResponseEntity con cuerpo ResponseObject con identificador del
	 *         pedido guardado
	 * @throws JOSEException
	 * @throws NotVerifiedSignedJWTException 
	 * @throws SignedJWTNullException 
	 * @throws IOException 
	 * @throws ParseException
	 */
	@ApiOperation(value = "Almacena un pedido", response = ResponseEntity.class)
	@ResponseBody
	@PostMapping(value = "/checkout")
	public ResponseEntity<ResponseObject> checkout(@RequestBody Request input, HttpServletRequest request)
			throws JOSEException, IOException, ParseException, SignedJWTNullException, NotVerifiedSignedJWTException {
		if (processHttpRequest(request).is2xxSuccessful()) {
			return ResponseEntity.ok(responseObjectService.checkout(input));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject());
	}

	/**
	 * Devuelve un listado de pedidos por identificador de usuario
	 * 
	 * @param input
	 * @param request
	 * @return Objeto ResponseEntity con cuerpo ResponseObject con listado de
	 *         pedidos relacionado al id del usuario
	 * @throws IOException
	 * @throws NotVerifiedSignedJWTException
	 * @throws SignedJWTNullException
	 * @throws JOSEException
	 * @throws ParseException
	 */
	@ApiOperation(value = "Lista los pedidos de un usuario", response = ResponseEntity.class)
	@ResponseBody
	@PostMapping(value = "/user")
	public ResponseEntity<ResponseObject> ordersByUser(@RequestBody Request input, HttpServletRequest request)
			throws ParseException, JOSEException, SignedJWTNullException, NotVerifiedSignedJWTException, IOException {
		if (processHttpRequest(request).is2xxSuccessful()) {
			return ResponseEntity.ok(responseObjectService.ordersByUser(input));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject());
	}

	/**
	 * Consume el servicio de validación de token con la cabecera recibida
	 * 
	 * @param request
	 * @return UNAUTHORIZED si el request o AUTHORIZATION o la validación del token
	 *         son no válidas
	 */
	private HttpStatus processHttpRequest(HttpServletRequest request) {
		if (request == null || request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
			return HttpStatus.UNAUTHORIZED;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		return restTemplate.exchange(tokenURL, HttpMethod.GET, entity, ResponseObject.class).getStatusCode();
	}

}

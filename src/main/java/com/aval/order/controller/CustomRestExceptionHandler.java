/*
 * @(#) CustomRestExceptionHandler.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aval.order.dto.ResponseObject;

/**
 * Clase encargada de controlar los errores del REST controller
 * 
 * @author Andrés Motavita
 *
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * Dada una excepción de tipo HttpClientErrorException, la controla y la dispone
	 * a un objeto personalizado
	 * 
	 * @param ex
	 * @return ResponseEntity<ResponseObject>
	 */
	@ExceptionHandler({ HttpClientErrorException.class })
	public ResponseEntity<ResponseObject> handleHttpClientError(final HttpClientErrorException ex) {
		logger.error(ex.getStatusText());
		logger.error(ex.getResponseBodyAsString());
		ResponseObject apiError = new ResponseObject();
		apiError.setCode(ex.getStatusCode().value());
		apiError.setMessage(ex.getStatusCode().getReasonPhrase());
		apiError.addPayload("error", ex.getResponseBodyAsString());
		return new ResponseEntity<>(apiError, ex.getResponseHeaders(), ex.getStatusCode());
	}
}

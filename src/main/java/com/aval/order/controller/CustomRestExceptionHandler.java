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

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aval.order.dto.ResponseObject;
import com.aval.order.exception.NotVerifiedSignedJWTException;
import com.aval.order.exception.SignedJWTNullException;

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

	/**
	 * Controla una petición que no se pueda leer
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error(ex.getMessage());
		logger.error(ex.getHttpInputMessage());
		ResponseObject apiError = new ResponseObject();
		apiError.setCode(status.value());
		apiError.setMessage(status.getReasonPhrase());
		apiError.addPayload("error", ex.getMessage());
		return new ResponseEntity<>(apiError, headers, status);
	}

	/**
	 * Controla las excepciones por JWT
	 * 
	 * @param ex
	 * @return ResponseEntity con ResponseObject
	 */
	@ExceptionHandler({ NotVerifiedSignedJWTException.class, SignedJWTNullException.class })
	public ResponseEntity<ResponseObject> handleSignedJWT(final Exception ex) {
		logger.error(ex.getMessage());
		ResponseObject apiError = new ResponseObject();
		apiError.setCode(HttpStatus.NOT_ACCEPTABLE.ordinal());
		apiError.setMessage(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
		apiError.setDetailedMessage(ex.getMessage());
		return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
	}
}

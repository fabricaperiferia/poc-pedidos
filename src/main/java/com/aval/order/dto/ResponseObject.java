/*
 * @(#) ResponseObject.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase POJO para respuesta del servicio REST
 * 
 * @author Andrés Motavita
 *
 */
@Getter
@Setter
public class ResponseObject {
	/**
	 * Código de respuesta
	 */
	private int code;
	/**
	 * Mensaje de respuesta
	 */
	private String message;
	/**
	 * Mensaje detallado
	 */
	private String detailedMessage;
	/**
	 * Carga de respuesta
	 */
	private Map<String, Object> payload;

	/**
	 * Constructor por defecto
	 */
	public ResponseObject() {
		this.payload = new HashMap<>();
	}

	/**
	 * Agregar una propiedad con su valor para respuesta
	 * 
	 * @param key   nombre de la propiedad de respuesta
	 * @param value Valor de la propiedad
	 * @return Object valor de la propiedad
	 */
	public Object addPayload(String key, Object value) {
		return this.payload.put(key, value);
	}

}
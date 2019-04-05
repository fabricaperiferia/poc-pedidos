/*
 * @(#) SignedJWTNullException.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.exception;

/**
 * Clase de excepción cuando no hay JWT firmado
 * 
 * @author Andrés Motavita
 *
 */
public class SignedJWTNullException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -6226118303943317405L;

	/**
	 * Constructor con parámetros
	 * 
	 * @param message
	 */
	public SignedJWTNullException(String message) {
		super(message);
	}
}

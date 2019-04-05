/*
 * @(#) NotVerifiedSignedJWTException.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.exception;

/**
 * Clase de excepción por no verificación de JWT firmado
 * 
 * @author Andrés Motavita
 *
 */
public class NotVerifiedSignedJWTException extends Exception {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7669570234881250526L;

	/**
	 * Constructor con parámetro
	 * 
	 * @param message
	 */
	public NotVerifiedSignedJWTException(String message) {
		super(message);
	}
}

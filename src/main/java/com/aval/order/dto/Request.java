/*
 * @(#) Request.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.dto;

import lombok.Data;

/**
 * Clase POJO de peticiones
 * 
 * @author Andrés Motavita
 *
 */
@Data
public class Request {
	/**
	 * JWT de petición
	 */
	private String token;
}

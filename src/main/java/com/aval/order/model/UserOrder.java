/*
 * @(#) UserOrder.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase modelo para redis
 * 
 * @author Andrés Motavita
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("userorders")
public class UserOrder implements Serializable {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -2831917882131787859L;
	/**
	 * Identificador de usuario
	 */
	@Id
	@Indexed
	private String userid;
	/**
	 * Listado de órdenes
	 */
	private List<Order> orders;
}

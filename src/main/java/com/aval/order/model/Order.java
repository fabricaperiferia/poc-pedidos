/*
 * @(#) Order.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

/**
 * Clase modelo para cassandra de entidad Orders
 * 
 * @author Andrés Motavita
 *
 */
@Data
@Table("Orders")
public class Order implements Serializable {
	/**
	 * Serial
	 */
	private static final long serialVersionUID = -708105114462311532L;
	/**
	 * Identificador
	 */
	@PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
	private UUID id;
	/**
	 * Identificador de Usuario
	 */
	@PrimaryKeyColumn(name = "userid", type = PrimaryKeyType.PARTITIONED)
	private String userId;
	/**
	 * Fecha de creación
	 */
	@Column
	private LocalDateTime creation;
	/**
	 * Productos
	 */
	@Column
	private String product;
}

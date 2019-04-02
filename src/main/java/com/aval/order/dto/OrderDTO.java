/*
 * @(#) OrderDTO.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase POJO para pedido
 * 
 * @author Andrés Motavita
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2091216570390121357L;
	/**
	 * Identificador del usuario
	 */
	private String userId;
	/**
	 * Fecha de creación
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creation;
	/**
	 * Listado de productos
	 */
	private List<ProductDTO> detail;
}

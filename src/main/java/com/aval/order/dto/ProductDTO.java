/*
 * @(#) ProductDTO.java
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
 * Clase POJO de producto
 * 
 * @author Andrés Motavita
 *
 */
@Data
public class ProductDTO {
	/**
	 * Identificador del producto
	 */
	private String productId;
	/**
	 * Precio del producto
	 */
	private Double price;
	/**
	 * Cantidad
	 */
	private Integer quantity;
	/**
	 * Nombre
	 */
	private String name;
	/**
	 * Presentación
	 */
	private String presentation;
	/**
	 * Categoría
	 */
	private String category;
	/**
	 * Descripción
	 */
	private String description;
	/**
	 * URL de la imagen
	 */
	private String image;
}

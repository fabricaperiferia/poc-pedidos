/*
 * @(#) JsonUtil.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.util;

import java.util.Collections;
import java.util.List;

import com.aval.order.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase utilitaria de operaciones con JSON
 * 
 * @author Andrés Motavita
 *
 */
public class JsonUtil {
	/**
	 * Constructor para evitar instancia
	 */
	private JsonUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Devuelve un listado de productDTO dado un JSON
	 * 
	 * @param json
	 * @param mapper
	 * @return
	 */
	public static List<ProductDTO> getList(String json, ObjectMapper mapper) {
		try {
			return mapper.readValue(json,
					mapper.getTypeFactory().constructCollectionType(List.class, ProductDTO.class));
		} catch (Exception e) {
			e.getStackTrace();
		}
		return Collections.emptyList();
	}
}

/*
 * @(#) UUIDToByteConverter.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.model.converter;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Clase Conversor escritor de UUID a Byte[] para redis
 * 
 * @author Andrés Motavita
 *
 */
@WritingConverter
public class UUIDToByteConverter implements Converter<UUID, byte[]> {

	@Override
	public byte[] convert(UUID source) {
		return source.toString().getBytes();
	}

}

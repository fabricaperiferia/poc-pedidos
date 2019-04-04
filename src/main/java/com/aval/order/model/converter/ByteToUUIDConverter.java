/*
 * @(#) ByteToUUIDConverter.java
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
import org.springframework.data.convert.ReadingConverter;

/**
 * Clase Conversor lector de Byte[] a UUID para redis
 * 
 * @author Andrés Motavita
 *
 */
@ReadingConverter
public class ByteToUUIDConverter implements Converter<byte[], UUID> {

	@Override
	public UUID convert(byte[] source) {
		return UUID.nameUUIDFromBytes(source);
	}

}

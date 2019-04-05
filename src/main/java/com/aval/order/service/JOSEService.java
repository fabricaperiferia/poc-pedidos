/*
 * @(#) JOSEService.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aval.order.exception.NotVerifiedSignedJWTException;
import com.aval.order.exception.SignedJWTNullException;
import com.aval.order.util.JOSEUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

/**
 * Clase de servicio para JOSE
 * 
 * @author Andrés Motavita
 *
 */
@Service
public class JOSEService {
	/**
	 * Distribuidor
	 */
	private static final String ISSUER = "poc-pedidos";
	/**
	 * Utilitario
	 */
	private JOSEUtil jose;
	/**
	 * Llave privada
	 */
	@Value("${app.private-key}")
	private String privateKey;
	/**
	 * Llave pública
	 */
	@Value("${app.public-key}")
	private String publicKey;

	/**
	 * Constructor
	 * 
	 * @throws JOSEException
	 */
	public JOSEService() throws JOSEException {
		jose = new JOSEUtil(privateKey, publicKey);
	}

	/**
	 * Dado un asunto, un nombre y un objeto, genera un JWT firmado y cifrado
	 * 
	 * @param subject
	 * @param name
	 * @param value
	 * @return JWT firmado y cifrado
	 * @throws JOSEException
	 */
	public String getToken(String subject, String name, Object value) throws JOSEException {
		JWTClaimsSet claims = new JWTClaimsSet.Builder().subject(subject).issueTime(new Date()).issuer(ISSUER)
				.claim(name, value).build();
		return jose.generateToken(claims);
	}

	/**
	 * Dado un JWT, un nombre de propiedad y su tipo, devuelve el objeto relacionado
	 * 
	 * @param token
	 * @param name
	 * @param valueType
	 * @return Objeto existente en los claims del JWT
	 * @throws ParseException
	 * @throws JOSEException
	 * @throws SignedJWTNullException
	 * @throws NotVerifiedSignedJWTException
	 * @throws IOException
	 */
	public <T> T getObjectFromToken(String token, String name, Class<T> valueType)
			throws ParseException, JOSEException, SignedJWTNullException, NotVerifiedSignedJWTException, IOException {
		JWTClaimsSet claims = jose.getClaimsFromToken(token);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue((String) claims.getClaim(name), valueType);
	}

}

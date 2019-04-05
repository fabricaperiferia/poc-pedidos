/*
 * @(#) JOSEUtil.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.util;

import java.text.ParseException;

import com.aval.order.exception.NotVerifiedSignedJWTException;
import com.aval.order.exception.SignedJWTNullException;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * Clase para el manejo de JOSE
 * 
 * @author Andrés Motavita
 *
 */
public class JOSEUtil {
	/**
	 * Tamaño de la llave
	 */
	private static final int KEY_SIZE = 2048;
	/**
	 * Llave RSA de envío
	 */
	private RSAKey sender;
	/**
	 * Llave RSA de recepción
	 */
	private RSAKey recipient;
	/**
	 * Cabecera para firmado
	 */
	private JWSHeader jwsheader;
	/**
	 * Cabecera para cifrado
	 */
	private JWEHeader jweheader;

	/**
	 * Constructor
	 * 
	 * @param privateKey
	 * @param publicKey
	 * @throws JOSEException
	 */
	public JOSEUtil(String privateKey, String publicKey) throws JOSEException {
		sender = new RSAKeyGenerator(KEY_SIZE).keyID(privateKey).keyUse(KeyUse.SIGNATURE).generate();
		jwsheader = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(sender.getKeyID()).build();
		jweheader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).contentType("JWT")
				.build();
		recipient = new RSAKeyGenerator(KEY_SIZE).keyID(publicKey).keyUse(KeyUse.ENCRYPTION).generate();
	}

	/**
	 * Dado un conjunto de claims, genera un JWT firmado y cifrado
	 * 
	 * @param claimsSet
	 * @return JWT firmado y cifrado
	 * @throws JOSEException
	 */
	public String generateToken(JWTClaimsSet claimsSet) throws JOSEException {
		SignedJWT signedJWT = new SignedJWT(jwsheader, claimsSet);
		JWEObject jweObject = new JWEObject(jweheader, new Payload(signedJWT));
		jweObject.encrypt(new RSAEncrypter(sender.toPublicJWK()));
		return jweObject.serialize();
	}

	/**
	 * Dado un JWT, descifra y evalúa la firma. Obtiene el conjunto de claims del
	 * mismo
	 * 
	 * @param token
	 * @return JWTClaimsSet del JWT
	 * @throws ParseException
	 * @throws JOSEException
	 * @throws SignedJWTNullException
	 * @throws NotVerifiedSignedJWTException
	 */
	public JWTClaimsSet getClaimsFromToken(String token)
			throws ParseException, JOSEException, SignedJWTNullException, NotVerifiedSignedJWTException {
		JWEObject jweObject = JWEObject.parse(token);
		jweObject.decrypt(new RSADecrypter(recipient));
		SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
		if (signedJWT == null) {
			throw new SignedJWTNullException("Not a signed JWT");
		}
		if (!signedJWT.verify(new RSASSAVerifier(sender.toPublicJWK()))) {
			throw new NotVerifiedSignedJWTException("No verified Signed JWT");
		}
		return signedJWT.getJWTClaimsSet();
	}
}

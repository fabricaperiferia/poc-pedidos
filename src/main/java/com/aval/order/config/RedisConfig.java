/*
 * @(#) RedisConfig.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.aval.order.model.converter.ByteToUUIDConverter;
import com.aval.order.model.converter.UUIDToByteConverter;

/**
 * Clase de configuración de conexión a redis
 * 
 * @author Andrés Motavita
 *
 */
@Configuration
@EnableRedisRepositories(basePackages = "com.aval.order.repository.redis")
public class RedisConfig {
	/**
	 * Host redis
	 */
	@Value("${spring.redis.host}")
	private String host;
	/**
	 * Puerto redis
	 */
	@Value("${spring.redis.port}")
	private int port;

	/**
	 * Bean de contexto de conexión lettuce
	 * 
	 * @return LettuceConnectionFactory
	 */
	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
	}

	/**
	 * Bean de contexto de conversiones personalizadas
	 * 
	 * @return RedisCustomConversions
	 */
	@Bean
	public RedisCustomConversions redisCustomConversions() {
		return new RedisCustomConversions(Arrays.asList(new UUIDToByteConverter(), new ByteToUUIDConverter()));
	}

	/**
	 * Bean de configuración del RedisTemplate
	 * 
	 * @return RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		return template;
	}
}

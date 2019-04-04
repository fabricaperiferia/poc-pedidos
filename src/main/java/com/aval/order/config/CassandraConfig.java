/*
 * @(#) CassandraConfig.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Clase de configuración de la conexión a cassandra
 * 
 * @author Andrés Motavita
 *
 */
@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

	/**
	 * Host
	 */
	@Value("${cassandra.contactpoints}")
	private String contactPoints;
	/**
	 * Puerto
	 */
	@Value("${cassandra.port}")
	private int port;
	/**
	 * Espacio de nombres
	 */
	@Value("${cassandra.keyspace}")
	private String keySpace;
	/**
	 * Paquetes a buscar objetos de cassandra
	 */
	@Value("${cassandra.basePackages}")
	private String basePackages;
	
	@Override
	protected String getKeyspaceName() {
		return keySpace;
	}

	@Override
	protected String getContactPoints() {
		return contactPoints;
	}

	@Override
	protected int getPort() {
		return port;
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { basePackages };
	}
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		List<String> listAllow = new ArrayList<>();
		listAllow.add("*");
		listAllow.add("http://localhost:8100");
		listAllow.add("http://172.16.15.71:8100");
		config.setAllowedOrigins(listAllow);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}

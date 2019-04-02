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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * Clase de configuración de la conexión a cassandra
 * 
 * @author Andrés Motavita
 *
 */
@Configuration
@EnableCassandraRepositories(basePackages = "com.aval.order.repository.cassandra")
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
}

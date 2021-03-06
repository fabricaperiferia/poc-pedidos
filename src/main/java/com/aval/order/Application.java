/*
 * @(#) Application.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.aval.order.config.SimpleCorsFilter;

/**
 * Clase principal encargada de iniciar la aplicación
 * 
 * @author Andrés Motavita
 *
 */
@EnableCaching
@SpringBootApplication
public class Application {
	/**
	 * Método principal para iniciar la aplicación
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Bean para el contexto de restTemplate para consumo de servicios
	 * 
	 * @return RestTemplate
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Bean de contexto para filtro CORS
	 * 
	 * @return CorsFilter
	 */
	@Bean
	public SimpleCorsFilter corsFilter() {
		return new SimpleCorsFilter();
	}
}

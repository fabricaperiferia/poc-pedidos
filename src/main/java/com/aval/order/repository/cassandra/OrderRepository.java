/*
 * @(#) OrderRepository.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.aval.order.model.Order;

/**
 * Interfaz de repositorio para entidad Order
 * 
 * @author Andrés Motavita
 *
 */
@Repository
public interface OrderRepository extends CassandraRepository<Order, UUID> {
	/**
	 * Lista las ordenes por identificador de usuario
	 * 
	 * @param userid
	 * @return
	 */
	public List<Order> findOrderByUserId(String userid);
}

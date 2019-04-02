/*
 * @(#) UserOrderRepository.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.repository.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aval.order.model.UserOrder;

/**
 * Interfaz repositorio para userorder en redis
 * 
 * @author Andrés Motavita
 *
 */
@Repository
public interface UserOrderRepository extends CrudRepository<UserOrder, String> {

}

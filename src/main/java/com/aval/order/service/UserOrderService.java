/*
 * @(#) UserOrderService.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aval.order.model.Order;
import com.aval.order.model.UserOrder;
import com.aval.order.repository.cassandra.OrderRepository;
import com.aval.order.repository.redis.UserOrderRepository;

/**
 * Clase servicio de userOrder
 * 
 * @author Andrés Motavita
 *
 */
@Service
public class UserOrderService {
	/**
	 * Repositorio UserOrder
	 */
	@Autowired
	private UserOrderRepository userOrderRepository;
	/**
	 * Repositorio Order
	 */
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * Dada un pedido, lo persiste y actualiza la cache
	 * 
	 * @param order
	 * @return UserOrder
	 */
	public UserOrder addOrder(Order order) {
		Optional<UserOrder> userOrder = userOrderRepository.findById(order.getUserId());
		UserOrder user = null;
		if (userOrder.isPresent()) {
			userOrder.get().getOrders().add(order);
			user = userOrder.get();
		} else {
			user = new UserOrder();
			user.setUserid(order.getUserId());
			user.setOrders(new ArrayList<>(Arrays.asList(order)));
		}
		return userOrderRepository.save(user);
	}
//	/**
//	 * 
//	 * @param userid
//	 * @return
//	 */
//	@Cacheable(value = "orders", key = "#userid")
	public List<Order> ordersByUser(String userid) {
		Optional<UserOrder> userOrder = userOrderRepository.findById(userid);
		if (userOrder.isPresent()) {
			return userOrder.get().getOrders();
		}
		List<Order> orders = orderRepository.findOrderByUserId(userid);
		userOrderRepository.save(new UserOrder(userid, orders));
		return orders;
	}
}

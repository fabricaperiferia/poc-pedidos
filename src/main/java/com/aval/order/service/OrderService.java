/*
 * @(#) OrderService.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aval.order.dto.OrderDTO;
import com.aval.order.model.Order;
import com.aval.order.repository.cassandra.OrderRepository;
import com.aval.order.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio de perdidos
 * 
 * @author Andrés Motavita
 *
 */
@Service
@Slf4j
public class OrderService {
	/**
	 * Repositorio de order
	 */
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserOrderService userOrderService;

	/**
	 * Dada una orden, la persiste
	 * 
	 * @param orderDTO
	 * @return ResponseObject con identificador del pedido
	 * @throws JsonProcessingException
	 */
	public Order checkout(OrderDTO orderDTO) throws JsonProcessingException {
		log.info("Ingresando {}", orderDTO.toString());
		ObjectMapper mapper = new ObjectMapper();
		String product = mapper.writeValueAsString(orderDTO.getDetail());
		Order order = new Order();
		order.setCreation(orderDTO.getCreation());
		order.setProduct(product);
		order.setUserId(orderDTO.getUserId());
		order.setId(UUID.randomUUID());
		orderRepository.insert(order);
		//userOrderService.addOrder(order);
		return order;
	}

	/**
	 * Dado un identificador de usuario, devuelve un listado de pedidos generados
	 * 
	 * @param userId
	 * @return Lista de pedidos
	 */
	public List<OrderDTO> ordersByUser(String userId) {
		log.info("consultando con userid {}", userId);
		ObjectMapper mapper = new ObjectMapper();
		List<Order> orders = userOrderService.ordersByUser(userId);
		return orders.stream()
				.map(e -> new OrderDTO(e.getUserId(), e.getCreation(), JsonUtil.getList(e.getProduct(), mapper)))
				.collect(Collectors.toList());
	}
}

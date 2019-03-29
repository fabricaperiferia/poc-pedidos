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
import com.aval.order.dto.ResponseObject;
import com.aval.order.model.Order;
import com.aval.order.repository.OrderRepository;
import com.aval.order.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servicio de perdidos
 * 
 * @author Andrés Motavita
 *
 */
@Service
public class OrderService {
	/**
	 * Repositorio de order
	 */
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * Dada una orden, la persiste
	 * 
	 * @param orderDTO
	 * @return ResponseObject con identificador del pedido
	 * @throws JsonProcessingException
	 */
	public ResponseObject checkout(OrderDTO orderDTO) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String product = mapper.writeValueAsString(orderDTO.getDetail());
		Order order = new Order();
		order.setCreation(orderDTO.getCreation());
		order.setProduct(product);
		order.setUserId(orderDTO.getUserId());
		order.setId(UUID.randomUUID());
		orderRepository.insert(order);
		ResponseObject response = new ResponseObject();
		response.setCode(1000);
		response.setMessage("SUCCESS");
		response.addPayload("orderId", order.getId());
		return response;
	}

	/**
	 * Dado un identificador de usuario, devuelve un listado de pedidos generados
	 * 
	 * @param userId
	 * @return Lista de pedidos
	 */
	public ResponseObject ordersByUser(String userId) {
		ResponseObject response = new ResponseObject();
		ObjectMapper mapper = new ObjectMapper();
		List<Order> orders = orderRepository.findOrderByUserId(userId);
		response.addPayload("Orders",
				orders.stream().map(
						e -> new OrderDTO(e.getUserId(), e.getCreation(), JsonUtil.getList(e.getProduct(), mapper)))
						.collect(Collectors.toList()));
		response.setCode(1000);
		response.setMessage("SUCCESS");
		return response;
	}
}

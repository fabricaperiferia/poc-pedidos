/*
 * @(#) ReponseObjectService.java
 * Copyright (c) Periferia IT Group, Todos Los Derechos Reservados
 * 
 * Este software es confidencial y propiedad de Periferia IT Group. El uso y
 * distribución está restringido a las actividades comerciales e internas de la
 * compañía.
 * 
 */
package com.aval.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aval.order.dto.OrderDTO;
import com.aval.order.dto.ResponseObject;
import com.aval.order.model.Order;
import com.aval.order.util.Codes;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Clase de servicio para ResponseObject
 * 
 * @author Andrés Motavita
 *
 */
@Service
public class ResponseObjectService {

	/**
	 * Servicio de pedidos
	 */
	@Autowired
	private OrderService orderService;

	/**
	 * Dada una orden, la persiste
	 * 
	 * @param orderDTO
	 * @return ResponseObject con identificador del pedido
	 * @throws JsonProcessingException
	 */
	public ResponseObject checkout(OrderDTO orderDTO) throws JsonProcessingException {
		ResponseObject response = new ResponseObject();
		response.setCode(Codes.SUCCESS.getValue());
		response.setMessage(Codes.SUCCESS.getLabel());
		Order order = orderService.checkout(orderDTO);
		response.addPayload("orderId", order.getId());
		return response;
	}

	/**
	 * Dado el identificador de un usuario, devuelve los pedidos de este usuario
	 * 
	 * @param userId identificador de usuario
	 * @return ResponseObject con listado de pedidos
	 */
	public ResponseObject ordersByUser(String userId) {
		ResponseObject response = new ResponseObject();
		List<OrderDTO> orders = orderService.ordersByUser(userId);
		response.addPayload("Orders", orders);
		response.setCode(Codes.SUCCESS.getValue());
		response.setMessage(Codes.SUCCESS.getLabel());
		return response;
	}
}

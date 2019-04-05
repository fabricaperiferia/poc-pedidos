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

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aval.order.dto.OrderDTO;
import com.aval.order.dto.Request;
import com.aval.order.dto.ResponseObject;
import com.aval.order.exception.NotVerifiedSignedJWTException;
import com.aval.order.exception.SignedJWTNullException;
import com.aval.order.model.Order;
import com.aval.order.util.Codes;
import com.nimbusds.jose.JOSEException;

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

	@Autowired
	private JOSEService joseService;

	/**
	 * Dada una orden, la persiste
	 * 
	 * @param orderDTO
	 * @return ResponseObject con identificador del pedido
	 * @throws JOSEException
	 * @throws IOException
	 * @throws NotVerifiedSignedJWTException
	 * @throws SignedJWTNullException
	 * @throws ParseException
	 */
	public ResponseObject checkout(Request input)
			throws JOSEException, IOException, ParseException, SignedJWTNullException, NotVerifiedSignedJWTException {
		ResponseObject response = new ResponseObject();
		response.setCode(Codes.SUCCESS.getValue());
		response.setMessage(Codes.SUCCESS.getLabel());
		OrderDTO orderDTO = joseService.getObjectFromToken(input.getToken(), "pedido", OrderDTO.class);
		Order order = orderService.checkout(orderDTO);
		response.addPayload("token", joseService.getToken("new order", "orderid", order.getId()));
		return response;
	}

	/**
	 * Dado el identificador de un usuario, devuelve los pedidos de este usuario
	 * 
	 * @param userId identificador de usuario
	 * @return ResponseObject con listado de pedidos
	 * @throws IOException
	 * @throws NotVerifiedSignedJWTException
	 * @throws SignedJWTNullException
	 * @throws JOSEException
	 * @throws ParseException
	 */
	public ResponseObject ordersByUser(Request input)
			throws ParseException, JOSEException, SignedJWTNullException, NotVerifiedSignedJWTException, IOException {
		ResponseObject response = new ResponseObject();
		String userId = joseService.getObjectFromToken(input.getToken(), "userid", String.class);
		List<OrderDTO> orders = orderService.ordersByUser(userId);
		response.addPayload("token", joseService.getToken("Orders by User", "orders", orders));
		response.setCode(Codes.SUCCESS.getValue());
		response.setMessage(Codes.SUCCESS.getLabel());
		return response;
	}
}

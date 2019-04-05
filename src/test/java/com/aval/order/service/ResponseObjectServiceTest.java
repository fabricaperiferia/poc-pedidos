package com.aval.order.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.aval.order.dto.OrderDTO;
import com.aval.order.dto.ResponseObject;
import com.aval.order.util.Codes;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResponseObjectServiceTest {

	private ResponseObjectService objectService;

	@Before
	public void setUp() {
		objectService = mock(ResponseObjectService.class);
	}

	@Test
	public void whenUserIdIsBlank_thenSuccessWithEmptyList() {
		String userid = null;
		ResponseObject response = new ResponseObject();
		response.addPayload("Orders", Collections.emptyList());
		response.setCode(Codes.SUCCESS.getValue());
		response.setMessage(Codes.SUCCESS.getLabel());
//		when(objectService.ordersByUser(userid)).thenReturn(response);
//		assertEquals("Codigo de respuesta satistactorio", Codes.SUCCESS.getValue(),
//				objectService.ordersByUser(userid).getCode());
//		assertEquals("Mensaje de respuesta satistactorio", Codes.SUCCESS.getLabel(),
//				objectService.ordersByUser(userid).getMessage());
//		@SuppressWarnings("unchecked")
//		List<OrderDTO> lista = (List<OrderDTO>) objectService.ordersByUser(userid).getPayload().get("Orders");
//		assertTrue("Lista vacía", lista.isEmpty());
	}
	
	@Test
	public void whenUserIdIsValid_thenSuccessWithNotEmptyList() {
		
	}
}

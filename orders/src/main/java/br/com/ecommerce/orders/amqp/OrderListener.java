package br.com.ecommerce.orders.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.ecommerce.orders.model.order.OrderStatus;
import br.com.ecommerce.orders.model.order.StatusTransitionDTO;
import br.com.ecommerce.orders.service.OrderService;
import jakarta.transaction.Transactional;

@Component
public class OrderListener {

	@Autowired
	private OrderService service;

	@RabbitListener(queues = "orders.status-payment")
	@Transactional
	public void consumesPaymentConfirmation(@Payload StatusTransitionDTO dto) {
		service.updateOrderStatus(dto.orderId(), OrderStatus.CONFIRMED_PAYMENT);
	}
	
//	/* 
//	 * Cart service create a Order
//	 */
//	@RabbitListener(queues = "orders.status-logistic")
//	@Transactional
//	public void recebeFilaMsgCartMock(@Payload OrderDTO dto) {
//		service.saveOrder(dto);
//	}
	
//	/* 
//	 * Mock a logistics mock service
//	 */
//	@RabbitListener(queues = "orders.status-logistic")
//	@Transactional
//	public void recebeFilaMsgLogisticaMock(@Payload StatusTransitionDTO dto) {
//		service.updateOrderStatus(dto.orderId(), dto.status());
//	}
}
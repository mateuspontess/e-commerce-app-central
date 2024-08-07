package br.com.ecommerce.products.api.amqp;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.ecommerce.products.api.dto.product.StockWriteOffDTO;
import br.com.ecommerce.products.business.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Component
public class ProductsListener {

	@Autowired
	private ProductService service;


	@RabbitListener(queues = "products.stock-orders")
	@Transactional
	public void receibeQueueMessageOrder(@Payload @Valid List<StockWriteOffDTO> dto) {
		service.updateStocks(dto);
	}
}
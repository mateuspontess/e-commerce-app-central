package br.com.ecommerce.products.api.dto.product;

import br.com.ecommerce.products.infra.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDTO {

	private Long productId;
	private String name;
	private Integer unit;

	public StockResponseDTO(Product product) {
		this.productId = product.getId();
		this.name = product.getName();
		this.unit = product.getStock().getUnit();
	}
}
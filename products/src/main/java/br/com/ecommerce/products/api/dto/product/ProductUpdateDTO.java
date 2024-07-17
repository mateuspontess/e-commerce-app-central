package br.com.ecommerce.products.api.dto.product;

import java.math.BigDecimal;

import br.com.ecommerce.products.api.dto.manufacturer.ManufacturerDTO;
import br.com.ecommerce.products.infra.entity.product.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTO {

	private String name;
	private String description;
	private BigDecimal price;
	private Category category;
	private ManufacturerDTO manufacturer;
}
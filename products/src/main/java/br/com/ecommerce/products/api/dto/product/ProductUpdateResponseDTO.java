package br.com.ecommerce.products.api.dto.product;

import java.math.BigDecimal;

import br.com.ecommerce.products.api.dto.manufacturer.ManufacturerDTO;
import br.com.ecommerce.products.infra.entity.product.Category;
import br.com.ecommerce.products.infra.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateResponseDTO {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private Category category;
	private ManufacturerDTO manufacturer;

	public ProductUpdateResponseDTO(Product p) {
		this.id = p.getId();
		this.name = p.getName();
		this.description = p.getDescription();
		this.price = p.getPrice();
		this.category = p.getCategory();
		this.manufacturer = new ManufacturerDTO(p.getManufacturer().getName());
	} 
}
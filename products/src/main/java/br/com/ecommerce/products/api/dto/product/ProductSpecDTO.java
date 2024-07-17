package br.com.ecommerce.products.api.dto.product;

import br.com.ecommerce.products.infra.entity.product.ProductSpec;
import jakarta.validation.constraints.NotBlank;
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
public class ProductSpecDTO {

	@NotBlank
	private String attribute;
	@NotBlank
	private String value;


	public ProductSpecDTO(ProductSpec p) {
		this.attribute = p.getAttribute();
		this.value = p.getValue();
	}
}
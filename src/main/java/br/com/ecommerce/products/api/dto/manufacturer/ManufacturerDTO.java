package br.com.ecommerce.products.api.dto.manufacturer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO{

	@NotBlank
	private String name;
}
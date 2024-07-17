package br.com.ecommerce.products.api.dto.manufacturer;

import br.com.ecommerce.products.infra.entity.manufacturer.Manufacturer;
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
public class ManufacturerResponseDTO{

	private Long id;
	private String name;


	public ManufacturerResponseDTO(Manufacturer manufacturer) {
		this.id = manufacturer.getId();
		this.name = manufacturer.getName();
	}
}
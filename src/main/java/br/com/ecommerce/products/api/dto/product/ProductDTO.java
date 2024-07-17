package br.com.ecommerce.products.api.dto.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.ecommerce.products.api.dto.manufacturer.ManufacturerDTO;
import br.com.ecommerce.products.infra.entity.product.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotNull
	private BigDecimal price;
	@NotNull
	private Category category;
	@NotNull
	@Valid
	private StockDTO stock; // cria junto com o objeto Product
	@NotNull
	@Valid
	private ManufacturerDTO manufacturer; // espera-se que já esteja criado no banco de dados
	@NotEmpty
	@Valid
	private List<ProductSpecDTO> specs = new ArrayList<>(); // cria junto com o objeto
}